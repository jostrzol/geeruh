package pl.edu.pw.elka.paprykaisalami.geeruh.utils;

import lombok.AllArgsConstructor;
import org.hibernate.envers.Audited;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Table;
import javax.persistence.metamodel.EntityType;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

@AllArgsConstructor
@Service
public class ResetDbService {

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
        var entityTypes = entityManager.getMetamodel()
                .getEntities().stream()
                .map(EntityType::getJavaType)
                .toList();
        var directTableNames = entityTypes.stream()
                .map(clazz -> clazz.getAnnotation(Table.class))
                .filter(Objects::nonNull)
                .map(Table::name);
        var auditedTableNames = entityTypes.stream()
                .filter(clazz -> clazz.getAnnotation(Audited.class) != null)
                .map(clazz -> clazz.getAnnotation(Table.class))
                .map(Table::name)
                .map(tableName -> tableName + "_AUD");
        return Stream.concat(directTableNames, auditedTableNames).toList();
    }
}
