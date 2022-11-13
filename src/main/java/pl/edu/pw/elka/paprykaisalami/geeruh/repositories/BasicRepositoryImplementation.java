package pl.edu.pw.elka.paprykaisalami.geeruh.repositories;

import java.util.Optional;
import java.util.function.Consumer;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Logger;

/**
 * The type Basic repository implementation.
 *
 * @param <T> the type parameter
 * @param <I> the type parameter
 */
public abstract class BasicRepositoryImplementation<T, I> implements BasicRepository<T, I> {
    private static final Logger LOGGER = (Logger) LoggerFactory.getLogger(BasicRepositoryImplementation.class);

    private final Class<T> objectClass;

    /**
     * The Entity manager.
     */
    protected final EntityManager entityManager;

    /**
     * Instantiates a new Basic repository implementation.
     *
     * @param entityManager the entity manager
     * @param objectClass   the object class
     */
    public BasicRepositoryImplementation(EntityManager entityManager, Class<T> objectClass) {
        this.entityManager = entityManager;
        this.objectClass = objectClass;
    }

    public Optional<T> save(T object) {
        EntityTransaction tx = null;
        try {
            tx = entityManager.getTransaction();
            tx.begin();
            entityManager.persist(object);
            tx.commit();
            return Optional.of(object);
        } catch (RuntimeException e) {
            if (tx != null && tx.isActive())
                tx.rollback();
            LOGGER.error("Could not save to repository", e);
            return Optional.empty();
        }
    }

    public Optional<T> findById(I id) {
        T object = entityManager.find(objectClass, id);
        return object != null ? Optional.of(object) : Optional.empty();
    }

    /**
     * Delete bindings.
     *
     * @param object the object
     */
    public abstract void deleteBindings(T object);

    public Boolean deleteById(I id) {
        T object = entityManager.find(objectClass, id);
        if (object != null) {
            EntityTransaction tx = null;
            try {
                tx = entityManager.getTransaction();
                tx.begin();

                deleteBindings(object);
                entityManager.remove(object);

                tx.commit();
            } catch (RuntimeException e) {
                if (tx != null && tx.isActive())
                    tx.rollback();
                LOGGER.error("Could not delete from repository", e);
                return false;
            }
            return true;
        }
        return false;
    }

    public Optional<T> alter(T object, Consumer<T> func) {
        EntityTransaction tx = null;
        try {
            tx = entityManager.getTransaction();
            tx.begin();
            var toEdit = entityManager.merge(object);
            func.accept(toEdit);
            tx.commit();
            return Optional.of(entityManager.merge(toEdit));
        } catch (RuntimeException e) {
            if (tx != null && tx.isActive())
                tx.rollback();
            LOGGER.error("Could not edit from repository", e);
            return Optional.empty();
        }
    }
}

