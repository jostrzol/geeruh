package pl.edu.pw.elka.paprykaisalami.geeruh.issues.adapters.api;

import pl.edu.pw.elka.paprykaisalami.geeruh.issues.domain.models.Issue;
import pl.edu.pw.elka.paprykaisalami.geeruh.issues.domain.models.IssueType;

public record IssueResponse(String issueId, IssueType type, String summary, String description) {

    public static IssueResponse of(Issue issue) {
        return new IssueResponse(
                issue.getIssueId().toString(),
                issue.getType(),
                issue.getSummary().value(),
                issue.getDescription().value()
        );
    }
}
