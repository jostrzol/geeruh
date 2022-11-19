package pl.edu.pw.elka.paprykaisalami.geeruh.issues.adapters.persistent;

import lombok.AllArgsConstructor;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.DefaultRevisionEntity;
import org.hibernate.envers.RevisionType;
import org.hibernate.envers.query.AuditEntity;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;
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
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
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
    @SuppressWarnings("unchecked")
    public Optional<ArrayList<IssueHistoryEntry>> getHistory(IssueId issueId) {
        AuditReader auditReader = AuditReaderFactory.get(entityManager);
        var resultList = auditReader
                .createQuery()
                .forRevisionsOfEntity(IssuePersistent.class, false, true)
                .add(AuditEntity.id().eq(issueId.getValue()))
                .getResultList();

        var history = new ArrayList<IssueHistoryEntry>();
        if (resultList != null) {
            for (Object[] d : (Collection<Object[]>) resultList) {
                final var entity = (IssuePersistent) d[0];
                final var revision = (DefaultRevisionEntity) d[1];
                final var revisionType = (RevisionType) d[2];
                var issueHistoryEntry = IssueHistoryEntry
                        .builder()
                        .timestamp(Timestamp.of(revision.getRevisionDate()))
                        .historicIssue(entity.toIssue())
                        .type(IssueHistoryEntryType.values()[revisionType.ordinal()])
                        .build();
                history.add(issueHistoryEntry);
            }
        }

        return Optional.of(history);
    }
}

@Component
interface ActualPersistentIssueRepository extends JpaRepository<IssuePersistent, UUID> {
}