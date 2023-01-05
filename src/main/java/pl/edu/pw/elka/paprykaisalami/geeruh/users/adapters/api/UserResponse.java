package pl.edu.pw.elka.paprykaisalami.geeruh.users.adapters.api;

import java.util.UUID;

import pl.edu.pw.elka.paprykaisalami.geeruh.users.domain.models.User;

public record UserResponse(
        UUID userId,
        String login,
        String email,
        String firstName,
        String secondName,
        String surname
) {

    public static UserResponse of(User user) {
        return new UserResponse(
                user.getUserId().value(),
                user.getLogin(),
                user.getEmail(),
                user.getFirstName(),
                user.getSecondName(),
                user.getSurname()
        );
    }
}
