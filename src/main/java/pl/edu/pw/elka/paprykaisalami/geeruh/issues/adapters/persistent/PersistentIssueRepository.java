package pl.edu.pw.elka.paprykaisalami.geeruh.issues.adapters.persistent;

import org.jetbrains.annotations.Nullable;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.pw.elka.paprykaisalami.geeruh.issues.domain.models.Description;
import pl.edu.pw.elka.paprykaisalami.geeruh.issues.domain.models.Issue;
import pl.edu.pw.elka.paprykaisalami.geeruh.issues.domain.models.IssueId;
import pl.edu.pw.elka.paprykaisalami.geeruh.issues.domain.models.IssueType;
import pl.edu.pw.elka.paprykaisalami.geeruh.issues.domain.models.Summary;
import pl.edu.pw.elka.paprykaisalami.geeruh.issues.domain.ports.IssueRepository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Primary
@Component
public class PersistentIssueRepository implements IssueRepository {

    final
    EntityManager entityManager;

    public PersistentIssueRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Issue> findAll() {
        return entityManager.createQuery("from IssuePersistent", IssuePersistent.class)
                .getResultList()
                .stream()
                .map(IssuePersistent::toIssue)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Issue> findById(IssueId issueId) {
        var ip = entityManager.find(IssuePersistent.class, issueId.getValue());
        if (ip == null) {
            return Optional.empty();
        }
        return Optional.ofNullable(ip.toIssue());
    }

    @Transactional
    @Override
    public Issue save(IssueType type, Summary summary, @Nullable Description description) {
        var issuePersistent = new IssuePersistent(type, summary, description);
        entityManager.persist(issuePersistent);
        return issuePersistent.toIssue();
    }
}
