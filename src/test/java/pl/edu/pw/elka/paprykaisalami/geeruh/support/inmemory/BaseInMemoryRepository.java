package pl.edu.pw.elka.paprykaisalami.geeruh.support.inmemory;

import io.vavr.control.Either;
import lombok.val;
import pl.edu.pw.elka.paprykaisalami.geeruh.support.idgenerators.IdGenerator;
import pl.edu.pw.elka.paprykaisalami.geeruh.utils.DomainError;
import pl.edu.pw.elka.paprykaisalami.geeruh.utils.DomainError.NotFoundDomainError;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public abstract class BaseInMemoryRepository<T, IdT> {

    private final Class<T> clazz;

    private final Map<IdT, T> map = new HashMap<>();

    private final Function<T, IdT> idExtractor;


    public BaseInMemoryRepository(Class<T> clazz, Function<T, IdT> idExtractor) {
        this.clazz = clazz;
        this.idExtractor = idExtractor;
    }

    public List<T> findAll() {
        return map.values().stream()
                .toList();
    }

    public Either<DomainError, T> findById(IdT id) {
        val entity = map.get(id);
        if (entity == null) {
            return Either.left(new NotFoundDomainError<>(clazz, id));
        } else {
            return Either.right(entity);
        }
    }

    public T save(T entity) {
        var id = idExtractor.apply(entity);
        map.put(id, entity);
        return entity;
    }
}
