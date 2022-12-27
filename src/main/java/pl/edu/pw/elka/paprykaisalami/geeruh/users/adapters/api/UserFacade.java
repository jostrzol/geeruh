package pl.edu.pw.elka.paprykaisalami.geeruh.users.adapters.api;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.edu.pw.elka.paprykaisalami.geeruh.users.domain.models.UserId;
import pl.edu.pw.elka.paprykaisalami.geeruh.users.domain.ports.UserService;
import pl.edu.pw.elka.paprykaisalami.geeruh.utils.DomainError;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Component
public class UserFacade {

    UserService userService;

    public List<UserResponse> list() {
        return userService.list().stream()
                .map(UserResponse::of)
                .toList();
    }

    public UserResponse get(UUID userId) {
        var user = userService.get(new UserId(userId))
                .getOrElseThrow(DomainError::toException);
        return UserResponse.of(user);
    }

    public UserResponse update(UUID userId, UserUpdateRequest request) {
        var user = userService.update(
                new UserId(userId),
                request.getFirstName(),
                request.getSecondName(),
                request.getSurname()
        ).getOrElseThrow(DomainError::toException);
        return UserResponse.of(user);
    }

    public UserResponse create(UserCreateRequest request) {
        var user = userService.create(
                request.getLogin(),
                request.getPassword(),
                request.getEmail(),
                request.getFirstName(),
                request.getSecondName(),
                request.getSurname()
        );
        return UserResponse.of(user);
    }
}
