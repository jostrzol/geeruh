package pl.edu.pw.elka.paprykaisalami.geeruh.repositories;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

/**
 * The interface Basic repository.
 *
 * @param <T> the type parameter
 * @param <I> the type parameter
 */
public interface BasicRepository<T, I> {
    /**
     * Save optional.
     *
     * @param object the object
     * @return the optional
     */
    Optional<T> save(T object);

    /**
     * Find by id optional.
     *
     * @param id the id
     * @return the optional
     */
    Optional<T> findById(I id);

    /**
     * Find all list.
     *
     * @return the list
     */
    List<T> findAll();

    /**
     * Delete by id boolean.
     *
     * @param id the id
     * @return if deleted
     */
    Boolean deleteById(I id);

    /**
     * Alter optional.
     *
     * @param object the object
     * @param func   the func
     * @return the optional
     */
    Optional<T> alter(T object, Consumer<T> func);
}
