package pl.edu.pw.elka.paprykaisalami.geeruh.security;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * This endpoint serves no real purpose.
 * It's just here for the springdoc, so that it can generate openapi with this endpoint included.
 */
@RestController
@Tag(name = "Login")
public class LoginEndpoint {

    @PostMapping("/login")
    void login(@RequestBody LoginRequest request) {
        throw new NotImplementedException("/login should not be called");
    }

    @GetMapping("/logout")
    void logout() {
        throw new NotImplementedException("/logout should not be called");
    }
}
