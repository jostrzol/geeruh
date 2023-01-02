package pl.edu.pw.elka.paprykaisalami.geeruh.issues.adapters.persistent;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.history.RevisionRepository;
import org.springframework.stereotype.Component;
import pl.edu.pw.elka.paprykaisalami.geeruh.issues.adapters.persistent.IssuePersistent.IssuePersistentId;

@Component
public
interface ActualPersistentIssueRepository extends
        JpaRepository<IssuePersistent, IssuePersistentId>,
        RevisionRepository<IssuePersistent, IssuePersistentId, Long> {
}
