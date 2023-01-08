package pl.edu.pw.elka.paprykaisalami.geeruh.utils;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@Service
@Profile("prod")
public class ResetDbServiceProd extends ResetDbService {

    public ResetDbServiceProd(EntityManager entityManager) {
        super(entityManager);
    }

    @Transactional
    public void resetDatabase() {
        entityManager.createNativeQuery("""
                DO $$ DECLARE
                    r RECORD;
                BEGIN
                    FOR r IN (SELECT tablename FROM pg_tables WHERE schemaname = current_schema()) LOOP
                        EXECUTE 'TRUNCATE TABLE ' || quote_ident(r.tablename) || ' CASCADE';
                    END LOOP;
                END $$;
                """).executeUpdate();
    }
}
