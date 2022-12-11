package pl.edu.pw.elka.paprykaisalami.geeruh.errors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Set;

record ApiErrors(Set<ApiError> errors) {

    ResponseEntity<Object> toResponseEntity(HttpStatus status) {
        return new ResponseEntity<>(errors, status);
    }

    static ApiErrors of(ApiError... error) {
        return new ApiErrors(Set.of(error));
    }
}
