package pl.edu.pw.elka.paprykaisalami.geeruh.users.adapters.persistent;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface ActualPersistentUserRepository extends JpaRepository<UserPersistent, UUID> {

    Optional<UserPersistent> findByLogin(String login);
}
