package pl.edu.pw.elka.paprykaisalami.geeruh.utils;

import lombok.AllArgsConstructor;
import org.hibernate.envers.Audited;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.Table;
import javax.persistence.metamodel.EntityType;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

@AllArgsConstructor
@Service
public abstract class ResetDbService {

    EntityManager entityManager;

    abstract public void resetDatabase();

    protected List<String> tableNames() {
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
