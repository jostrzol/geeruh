package pl.edu.pw.elka.paprykaisalami.geeruh.utils;

import io.vavr.control.Either;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import pl.edu.pw.elka.paprykaisalami.geeruh.errors.ApiError;
import pl.edu.pw.elka.paprykaisalami.geeruh.errors.ErrorsException;

import java.util.function.Supplier;

public abstract sealed class DomainError {

    public ErrorsException toException() {
        return ErrorsException.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .error(ApiError.builder()
                               .code("INTERNAL_ERROR")
                               .message("Internal error")
                               .build()
                )
                .build();
    }

    @AllArgsConstructor
    public static final class NotFoundDomainError<T, IdT> extends DomainError {

        Class<T> clazz;

        IdT id;

        @Override
        public ErrorsException toException() {
            return ErrorsException.builder()
                    .status(HttpStatus.NOT_FOUND)
                    .error(ApiError.builder()
                                   .code("NOT_FOUND")
                                   .message("Resource not found")
                                   .withContext("id", id.toString())
                                   .withContext("resource", clazz.getSimpleName())
                                   .build()
                    )
                    .build();
        }

        public static <T, IdT, RT> Supplier<Either<DomainError, RT>> supplier(Class<T> clazz, IdT id) {
            return () -> Either.left(new NotFoundDomainError<>(clazz, id));
        }
    }
}
