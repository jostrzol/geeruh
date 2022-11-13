package pl.edu.pw.elka.paprykaisalami.geeruh.issues.adapters.inmemoryrepository;

import lombok.val;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Component;
import pl.edu.pw.elka.paprykaisalami.geeruh.issues.domain.models.*;
import pl.edu.pw.elka.paprykaisalami.geeruh.issues.domain.ports.IssueRepository;

import java.util.*;

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
    public Issue save(IssueType type, Summary summary, @Nullable Description description) {
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
}
