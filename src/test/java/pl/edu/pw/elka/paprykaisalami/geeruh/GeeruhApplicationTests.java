package pl.edu.pw.elka.paprykaisalami.geeruh;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import pl.edu.pw.elka.paprykaisalami.geeruh.repositories.StatusRepository;

@SpringBootTest
class GeeruhApplicationTests {
    protected static final String PERSISTANCE_UNIT = "geeruh.test.sn";

    protected final EntityManagerFactory emf = Persistence.createEntityManagerFactory(PERSISTANCE_UNIT);

    protected final EntityManager entityManager = emf.createEntityManager();

    StatusRepository statusRepository = new StatusRepository(entityManager);

    @Test
    void contextLoads() {
        statusRepository.findAll();
    }

}
