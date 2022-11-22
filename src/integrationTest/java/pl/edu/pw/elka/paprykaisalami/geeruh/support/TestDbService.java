package pl.edu.pw.elka.paprykaisalami.geeruh.support;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.metamodel.EntityType;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class TestDbService {

    EntityManagerFactory entityManagerFactory;

    EntityManager entityManager;

    @Transactional
    public void resetDatabase() {
        entityManager.createNativeQuery("SET REFERENTIAL_INTEGRITY FALSE").executeUpdate();

        for (String tableName : tableNames()) {
            entityManager.createNativeQuery("TRUNCATE TABLE " + tableName)
                    .executeUpdate();
        }

        entityManager.createNativeQuery("SET REFERENTIAL_INTEGRITY TRUE").executeUpdate();
    }

    private List<String> tableNames() {
        return List.of("Issues", "Issues_AUD");
//        var metamodel = entityManagerFactory.getMetamodel();
//        return metamodel.getEntities().stream()
//                .map(EntityType::getJavaType)
//                .map(clazz -> clazz.getAnnotation(Table.class))
//                .map(Table::name)
//                .collect(Collectors.toList());
    }
}
