package pl.edu.pw.elka.paprykaisalami.geeruh.utils;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@Service
@Profile("!prod")
public class ResetDbServiceDev extends ResetDbService {

    public ResetDbServiceDev(EntityManager entityManager) {
        super(entityManager);
    }

    @Transactional
    public void resetDatabase() {
        entityManager.createNativeQuery("SET REFERENTIAL_INTEGRITY FALSE").executeUpdate();

        for (String tableName : tableNames()) {
            entityManager.createNativeQuery("TRUNCATE TABLE " + tableName)
                    .executeUpdate();
        }

        entityManager.createNativeQuery("SET REFERENTIAL_INTEGRITY TRUE").executeUpdate();
    }
}

