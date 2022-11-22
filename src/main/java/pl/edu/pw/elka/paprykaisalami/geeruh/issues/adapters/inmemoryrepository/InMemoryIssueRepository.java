package pl.edu.pw.elka.paprykaisalami.geeruh.issues.adapters.inmemoryrepository;

import lombok.val;
import org.springframework.stereotype.Component;
import pl.edu.pw.elka.paprykaisalami.geeruh.issues.domain.models.Description;
import pl.edu.pw.elka.paprykaisalami.geeruh.issues.domain.models.Issue;
import pl.edu.pw.elka.paprykaisalami.geeruh.issues.domain.models.IssueHistoryEntry;
import pl.edu.pw.elka.paprykaisalami.geeruh.issues.domain.models.IssueId;
import pl.edu.pw.elka.paprykaisalami.geeruh.issues.domain.models.IssueType;
import pl.edu.pw.elka.paprykaisalami.geeruh.issues.domain.models.Summary;
import pl.edu.pw.elka.paprykaisalami.geeruh.issues.domain.ports.IssueRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Component
public class InMemoryIssueRepository implements IssueRepository {

    private final Map<IssueId, Issue> issues = new HashMap<>();

    @Override
    public List<Issue> findAll() {
        return issues.values().stream()
                .toList();
    }

    @Override
    public Optional<Issue> findById(IssueId issueId) {
        val issue = issues.get(issueId);
        return Optional.ofNullable(issue);
    }

    @Override
    public Issue create(IssueType type, Summary summary, Description description) {
        val issueId = IssueId.of(UUID.randomUUID());
        val issue = Issue.builder()
                .issueId(issueId)
                .type(type)
                .summary(summary)
                .description(description)
                .build();
        issues.put(issueId, issue);
        return issue;
    }

    @Override
    public Issue save(Issue issue) {
        issues.put(issue.getIssueId(), issue);
        return issue;
    }

    @Override
    public List<IssueHistoryEntry> getHistory(IssueId issueId) {
        return List.of();
    }
}
