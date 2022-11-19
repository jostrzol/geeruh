package pl.edu.pw.elka.paprykaisalami.geeruh.issues.adapters.persistent;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.data.history.Revisions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.history.RevisionRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.pw.elka.paprykaisalami.geeruh.issues.domain.models.Description;
import pl.edu.pw.elka.paprykaisalami.geeruh.issues.domain.models.Issue;
import pl.edu.pw.elka.paprykaisalami.geeruh.issues.domain.models.IssueHistoryEntry;
import pl.edu.pw.elka.paprykaisalami.geeruh.issues.domain.models.IssueHistoryEntryType;
import pl.edu.pw.elka.paprykaisalami.geeruh.issues.domain.models.IssueId;
import pl.edu.pw.elka.paprykaisalami.geeruh.issues.domain.models.IssueType;
import pl.edu.pw.elka.paprykaisalami.geeruh.issues.domain.models.Summary;
import pl.edu.pw.elka.paprykaisalami.geeruh.issues.domain.models.Timestamp;
import pl.edu.pw.elka.paprykaisalami.geeruh.issues.domain.ports.IssueRepository;

import javax.persistence.EntityManager;
import java.sql.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@AllArgsConstructor
@Primary
@Component
class PersistentIssueRepository implements IssueRepository {

    ActualPersistentIssueRepository actualRepository;

    IssueRevisionRepository issueRevisionRepository;

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
        return actualRepository.findById(issueId.getValue())
                .map(IssuePersistent::toIssue);
    }

    @Transactional
    @Override
    public Issue save(IssueType type, Summary summary, Description description) {
        var issuePersistent = new IssuePersistent(type, summary, description);
        return actualRepository.save(issuePersistent)
                .toIssue();
    }

    @Override
    public Optional<Issue> update(Issue issue) {
        var issuePersistentRes = actualRepository.findById(issue.getIssueId().getValue());
        if (issuePersistentRes.isEmpty()) {
            return Optional.empty();
        }
        var issuePersistent = issuePersistentRes.get();
        issuePersistent.setDescription(issue.getDescription().getValue());
        issuePersistent.setSummary(issue.getSummary().getValue());
        issuePersistent.setType(issue.getType());
        return Optional.ofNullable(actualRepository.save(issuePersistent).toIssue());
    }

    @Override
    public Optional<List<IssueHistoryEntry>> getHistory(IssueId issueId) {
        Revisions<Long, IssuePersistent> revisions = issueRevisionRepository.findRevisions(issueId.getValue());


        var history = revisions.stream().map(rev -> IssueHistoryEntry
                .builder()
                .timestamp(Timestamp.of(Date.from(rev.getMetadata().getRequiredRevisionInstant())))
                .historicIssue(rev.getEntity().toIssue())
                .type(IssueHistoryEntryType.values()[rev.getMetadata().getRevisionType().ordinal()])
                .build()).collect(Collectors.toList());

        return Optional.of(history);
    }
}

@Component
interface ActualPersistentIssueRepository extends JpaRepository<IssuePersistent, UUID> {
}

@Component
interface IssueRevisionRepository
        extends CrudRepository<IssuePersistent, UUID>,
        RevisionRepository<IssuePersistent, UUID, Long> {
}