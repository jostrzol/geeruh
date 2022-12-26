package pl.edu.pw.elka.paprykaisalami.geeruh.users.domain.ports;


import io.vavr.control.Either;
import pl.edu.pw.elka.paprykaisalami.geeruh.users.domain.models.User;
import pl.edu.pw.elka.paprykaisalami.geeruh.users.domain.models.UserId;
import pl.edu.pw.elka.paprykaisalami.geeruh.utils.DomainError;

import java.util.List;

public interface UserRepository {

    List<User> findAll();

    Either<DomainError, User> findById(UserId userId);

    Either<DomainError, User> findByLogin(String login);

    User save(User user);
}
