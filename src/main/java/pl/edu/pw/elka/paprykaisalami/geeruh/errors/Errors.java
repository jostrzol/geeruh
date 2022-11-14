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
class Errors {

    Set<Error> errors;

    ResponseEntity<Object> toResponseEntity(HttpStatus status) {
        return new ResponseEntity<>(errors, status);
    }

    static Errors of(Error... error) {
        return new Errors(Set.of(error));
    }
}
