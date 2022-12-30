package pl.edu.pw.elka.paprykaisalami.geeruh.issues.adapters.api;

import java.util.UUID;

import pl.edu.pw.elka.paprykaisalami.geeruh.issues.domain.models.Issue;
import pl.edu.pw.elka.paprykaisalami.geeruh.issues.domain.models.IssueType;

public record IssueResponse(
        String issueId,
        String statusCode,
        IssueType type,
        String summary,
        String description,
        UUID assigneeUserId,
        String relatedIssueId) {

    public static IssueResponse of(Issue issue) {
        var assigneeUserId = issue.getAssigneeUserId();
        var relatedIssueId = issue.getRelatedIssueId();
        return new IssueResponse(
                issue.getIssueId().toString(),
                issue.getStatusCode().value(),
                issue.getType(),
                issue.getSummary().value(),
                issue.getDescription().value(),
                assigneeUserId == null ? null : assigneeUserId.value(),
                relatedIssueId == null ? null : relatedIssueId.toString());
    }
}
