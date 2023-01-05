package pl.edu.pw.elka.paprykaisalami.geeruh.spring.security;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.pw.elka.paprykaisalami.geeruh.users.adapters.api.UserResponse;
import pl.edu.pw.elka.paprykaisalami.geeruh.users.domain.ports.UserService;
import pl.edu.pw.elka.paprykaisalami.geeruh.utils.DomainError;

import java.security.Principal;

@AllArgsConstructor
@Component
@Tag(name = "Login")
@RestController
public class LoginEndpoint {

    private UserService userService;

    @PostMapping("/login")
    void login(@RequestBody LoginRequest request) {
        throw new NotImplementedException("/login should not be called");
    }

    @GetMapping("/logout")
    void logout() {
        throw new NotImplementedException("/logout should not be called");
    }

    @GetMapping("/session")
    UserResponse getSession(Principal principal) {
        var user = userService.getByLogin(principal.getName())
                .getOrElseThrow(DomainError::toException);
        return UserResponse.of(user);
    }
}
