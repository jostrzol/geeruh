package pl.edu.pw.elka.paprykaisalami.geeruh.issues.adapters.api;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import pl.edu.pw.elka.paprykaisalami.geeruh.issues.domain.models.Issue;
import pl.edu.pw.elka.paprykaisalami.geeruh.issues.domain.models.IssueId;
import pl.edu.pw.elka.paprykaisalami.geeruh.issues.domain.models.IssueType;

public record IssueResponse(
        String issueId,
        String statusCode,
        IssueType type,
        String summary,
        String description,
        UUID assigneeUserId,
        Set<String> relatedIssues,
        Set<String> relatedIssuesChildren) {

    public static IssueResponse of(Issue issue) {
        var assigneeUserId = issue.getAssigneeUserId();
        return new IssueResponse(
                issue.getIssueId().toString(),
                issue.getStatusCode().value(),
                issue.getType(),
                issue.getSummary().value(),
                issue.getDescription().value(),
                assigneeUserId == null ? null : assigneeUserId.value(),
                issue.getRelatedIssues().stream().map(IssueId::toString).collect(Collectors.toSet()),
                issue.getRelatedIssuesChildren().stream().map(IssueId::toString).collect(Collectors.toSet()));
    }
}
