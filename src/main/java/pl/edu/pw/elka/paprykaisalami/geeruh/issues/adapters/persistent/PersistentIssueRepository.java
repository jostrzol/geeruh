package pl.edu.pw.elka.paprykaisalami.geeruh.issues.adapters.persistent;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.history.RevisionRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.pw.elka.paprykaisalami.geeruh.issues.adapters.persistent.IssuePersistent.IssuePersistentId;
import pl.edu.pw.elka.paprykaisalami.geeruh.issues.domain.models.Description;
import pl.edu.pw.elka.paprykaisalami.geeruh.issues.domain.models.Issue;
import pl.edu.pw.elka.paprykaisalami.geeruh.issues.domain.models.IssueHistoryEntry;
import pl.edu.pw.elka.paprykaisalami.geeruh.issues.domain.models.IssueHistoryEntryType;
import pl.edu.pw.elka.paprykaisalami.geeruh.issues.domain.models.IssueId;
import pl.edu.pw.elka.paprykaisalami.geeruh.issues.domain.models.IssueType;
import pl.edu.pw.elka.paprykaisalami.geeruh.issues.domain.models.Summary;
import pl.edu.pw.elka.paprykaisalami.geeruh.issues.domain.ports.IssueRepository;
import pl.edu.pw.elka.paprykaisalami.geeruh.projects.domain.models.ProjectCode;

import javax.persistence.EntityManager;
import java.sql.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@Primary
@Component
class PersistentIssueRepository implements IssueRepository {

    ActualPersistentIssueRepository actualRepository;

    EntityManager entityManager;

    @Override
    public List<Issue> findAll() {
        return actualRepository.findAll()
                .stream()
                .map(IssuePersistent::toIssue)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Issue> findById(IssueId issueId) {
        return actualRepository.findById(IssuePersistentId.of(issueId))
                .map(IssuePersistent::toIssue);
    }

    @Transactional
    @Override
    public Issue create(ProjectCode projectCode, IssueType type, Summary summary, Description description) {
        var issuePersistent = new IssuePersistent(projectCode, type, summary, description);
        return actualRepository.save(issuePersistent)
                .toIssue();
    }

    @Override
    public Issue save(Issue issue) {
        var issuePersistent = IssuePersistent.of(issue);
        return actualRepository.save(issuePersistent).toIssue();
    }

    @Override
    public List<IssueHistoryEntry> getHistory(IssueId issueId) {
        var revisions = actualRepository.findRevisions(IssuePersistentId.of(issueId));
        return revisions.stream().map(rev -> IssueHistoryEntry
                .builder()
                .timestamp(Date.from(rev.getMetadata().getRequiredRevisionInstant()))
                .historicIssue(rev.getEntity().toIssue())
                .type(IssueHistoryEntryType.values()[rev.getMetadata().getRevisionType().ordinal()])
                .build()).collect(Collectors.toList());
    }
}

@Component
interface ActualPersistentIssueRepository extends
        JpaRepository<IssuePersistent, IssuePersistentId>,
        RevisionRepository<IssuePersistent, IssuePersistentId, Long> {
}