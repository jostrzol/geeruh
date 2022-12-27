package pl.edu.pw.elka.paprykaisalami.geeruh.spring.errors;

import lombok.Builder;
import lombok.Getter;
import lombok.Singular;
import lombok.val;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Set;

@Getter
@Builder
public class ErrorsException extends RuntimeException {

    HttpStatus status;

    @Singular
    Set<ApiError> errors;

    public ResponseEntity<Object> toResponseEntity() {
        val errorArray = errors.toArray(ApiError[]::new);
        return ApiErrors.of(errorArray)
                .toResponseEntity(status);
    }
}
