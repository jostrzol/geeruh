package pl.edu.pw.elka.paprykaisalami.geeruh.users.domain.ports;

import io.vavr.control.Either;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import pl.edu.pw.elka.paprykaisalami.geeruh.users.domain.models.User;
import pl.edu.pw.elka.paprykaisalami.geeruh.users.domain.models.UserId;
import pl.edu.pw.elka.paprykaisalami.geeruh.utils.DomainError;

import javax.validation.Valid;
import java.util.List;

import static pl.edu.pw.elka.paprykaisalami.geeruh.users.domain.models.UserId.randomUserId;

@AllArgsConstructor
@Validated
@Component
@Transactional(readOnly = true)
public class UserService {

    UserRepository userRepository;

    UserPasswordEncoder passwordEncoder;

    public List<User> list() {
        return userRepository.findAll();
    }

    public Either<DomainError, User> get(UserId userId) {
        return userRepository.findById(userId);
    }

    @Valid
    @Transactional
    public User create(
            String login,
            String password,
            String email,
            String firstName,
            String secondName,
            String surname) {
        var user = User.builder()
                .userId(randomUserId())
                .login(login)
                .passwordHash(passwordEncoder.encode(password))
                .email(email)
                .firstName(firstName)
                .secondName(secondName)
                .surname(surname)
                .build();
        return userRepository.save(user);
    }

    @Valid
    @Transactional
    public Either<DomainError, User> update(UserId userId, String firstName, String secondName, String surname) {
        return userRepository.findById(userId).map(
                user -> {
                    user.setFirstName(firstName);
                    user.setSecondName(secondName);
                    user.setSurname(surname);
                    return userRepository.save(user);
                });
    }

}
