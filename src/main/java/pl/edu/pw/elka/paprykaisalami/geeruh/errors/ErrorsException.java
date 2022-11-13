package pl.edu.pw.elka.paprykaisalami.geeruh.errors;

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
    Set<Error> errors;

    public ResponseEntity<Object> toResponseEntity() {
        val errorArray = errors.toArray(new Error[0]);
        return Errors.of(errorArray)
                .toResponseEntity(status);
    }

    public static ErrorsException notFound(String resource) {
        val code = String.format("%s_NOT_FOUND", resource.toUpperCase());
        val message = String.format("Resource '%s' not found.", resource);

        return ErrorsException.builder()
                .status(HttpStatus.NOT_FOUND)
                .error(Error.builder()
                        .code(code)
                        .message(message)
                        .build()
                )
                .build();
    }
}
