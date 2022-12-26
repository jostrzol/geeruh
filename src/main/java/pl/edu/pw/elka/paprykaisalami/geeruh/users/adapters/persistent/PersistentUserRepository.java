package pl.edu.pw.elka.paprykaisalami.geeruh.users.adapters.persistent;

import io.vavr.control.Either;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import pl.edu.pw.elka.paprykaisalami.geeruh.users.domain.models.User;
import pl.edu.pw.elka.paprykaisalami.geeruh.users.domain.models.UserId;
import pl.edu.pw.elka.paprykaisalami.geeruh.users.domain.ports.UserRepository;
import pl.edu.pw.elka.paprykaisalami.geeruh.utils.DomainError;
import pl.edu.pw.elka.paprykaisalami.geeruh.utils.DomainError.NotFoundDomainError;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@AllArgsConstructor
@Primary
@Component
class PersistentUserRepository implements UserRepository {

    ActualPersistentUserRepository actualRepository;

    @Override
    public List<User> findAll() {
        return actualRepository.findAll()
                .stream()
                .map(UserPersistent::toUser)
                .collect(Collectors.toList());
    }

    @Override
    public Either<DomainError, User> findById(UserId userId) {
        return actualRepository.findById(userId.value())
                .<Either<DomainError, UserPersistent>>map(Either::right)
                .orElseGet(NotFoundDomainError.supplier(User.class, userId))
                .map(UserPersistent::toUser);
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public User save(User issue) {
        var userPersistent = UserPersistent.of(issue);
        return actualRepository.save(userPersistent).toUser();
    }
}

@Component
interface ActualPersistentUserRepository extends JpaRepository<UserPersistent, UUID> {
}
