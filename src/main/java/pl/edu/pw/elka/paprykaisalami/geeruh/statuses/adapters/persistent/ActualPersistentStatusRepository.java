package pl.edu.pw.elka.paprykaisalami.geeruh.statuses.adapters.persistent;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public
interface ActualPersistentStatusRepository extends JpaRepository<StatusPersistent, String> {
}
