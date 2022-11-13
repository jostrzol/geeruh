package pl.edu.pw.elka.paprykaisalami.geeruh.repositories;

import java.util.List;
import java.util.UUID;

import javax.persistence.EntityManager;

import pl.edu.pw.elka.paprykaisalami.geeruh.models.Status;

public class StatusRepository extends BasicRepositoryImplementation<Status, UUID> {
    /**
     * Instantiates a new Basic repository implementation.
     *
     * @param entityManager the entity manager
     */
    public StatusRepository(EntityManager entityManager) {
        super(entityManager, Status.class);
    }

    @Override
    public List<Status> findAll() {
        return entityManager.createQuery("from Status", Status.class).getResultList();
    }

    @Override
    public void deleteBindings(Status object) {

    }
}
