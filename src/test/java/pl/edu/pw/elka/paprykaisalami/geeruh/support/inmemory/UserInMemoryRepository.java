package pl.edu.pw.elka.paprykaisalami.geeruh.support.inmemory;

import io.vavr.control.Either;
import pl.edu.pw.elka.paprykaisalami.geeruh.issues.adapters.persistent.IssuePersistent;
import pl.edu.pw.elka.paprykaisalami.geeruh.projects.domain.models.Project;
import pl.edu.pw.elka.paprykaisalami.geeruh.projects.domain.models.ProjectCode;
import pl.edu.pw.elka.paprykaisalami.geeruh.projects.domain.ports.ProjectRepository;
import pl.edu.pw.elka.paprykaisalami.geeruh.users.domain.models.User;
import pl.edu.pw.elka.paprykaisalami.geeruh.users.domain.models.UserId;
import pl.edu.pw.elka.paprykaisalami.geeruh.users.domain.ports.UserRepository;
import pl.edu.pw.elka.paprykaisalami.geeruh.utils.DomainError;
import pl.edu.pw.elka.paprykaisalami.geeruh.utils.DomainError.NotFoundDomainError;

public class UserInMemoryRepository extends BaseInMemoryRepository<User, UserId> implements UserRepository {

    public UserInMemoryRepository() {
        super(User.class, User::getUserId);
    }

    @Override
    public Either<DomainError, User> findByLogin(String login) {
        return map.values().stream()
                .filter(user -> user.getLogin().equals(login))
                .findFirst()
                .<Either<DomainError, User>>map(Either::right)
                .orElseGet(NotFoundDomainError.supplier(User.class, login));
    }
}
