package pl.edu.pw.elka.paprykaisalami.geeruh.errors;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Set;

@Jacksonized
@Builder
@Value
class ApiErrors {

    Set<ApiError> errors;

    ResponseEntity<Object> toResponseEntity(HttpStatus status) {
        return new ResponseEntity<>(errors, status);
    }

    static ApiErrors of(ApiError... error) {
        return new ApiErrors(Set.of(error));
    }
}
