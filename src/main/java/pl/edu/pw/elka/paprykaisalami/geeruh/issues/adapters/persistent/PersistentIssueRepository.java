package pl.edu.pw.elka.paprykaisalami.geeruh.issues.adapters.persistent;

import io.vavr.control.Either;
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
import pl.edu.pw.elka.paprykaisalami.geeruh.statuses.adapters.persistent.ActualPersistentStatusRepository;
import pl.edu.pw.elka.paprykaisalami.geeruh.statuses.domain.models.StatusCode;
import pl.edu.pw.elka.paprykaisalami.geeruh.utils.DomainError;
import pl.edu.pw.elka.paprykaisalami.geeruh.utils.DomainError.NotFoundDomainError;

import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Primary
@Component
class PersistentIssueRepository implements IssueRepository {

    ActualPersistentIssueRepository actualRepository;

    ActualPersistentStatusRepository actualPersistentStatusRepository;

    @Override
    public List<Issue> findAll() {
        return actualRepository.findAll()
                .stream()
                .map(IssuePersistent::toIssue)
                .collect(Collectors.toList());
    }

    @Override
    public Either<DomainError, Issue> findById(IssueId issueId) {
        return actualRepository.findById(IssuePersistentId.of(issueId))
                .<Either<DomainError, IssuePersistent>>map(Either::right)
                .orElseGet(NotFoundDomainError.supplier(Issue.class, issueId))
                .map(IssuePersistent::toIssue);
    }

    @Override
    public Issue create(ProjectCode projectCode, StatusCode statusCode, IssueType type, Summary summary, Description description) {
        var issuePersistent = new IssuePersistent(
                projectCode,
                actualPersistentStatusRepository.getReferenceById(statusCode.value()),
                type,
                summary,
                description);
        return actualRepository.save(issuePersistent)
                .toIssue();
    }

    @Override
    public Issue save(Issue issue) {
        var status = actualPersistentStatusRepository.getReferenceById(issue.getStatusCode().value());
        var issuePersistent = IssuePersistent.of(issue, status);
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