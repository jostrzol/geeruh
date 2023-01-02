package pl.edu.pw.elka.paprykaisalami.geeruh.issues.adapters.api;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.edu.pw.elka.paprykaisalami.geeruh.issues.domain.models.Description;
import pl.edu.pw.elka.paprykaisalami.geeruh.issues.domain.models.IssueId;
import pl.edu.pw.elka.paprykaisalami.geeruh.issues.domain.models.Summary;
import pl.edu.pw.elka.paprykaisalami.geeruh.issues.domain.ports.IssueService;
import pl.edu.pw.elka.paprykaisalami.geeruh.projects.domain.models.ProjectCode;
import pl.edu.pw.elka.paprykaisalami.geeruh.statuses.domain.models.StatusCode;
import pl.edu.pw.elka.paprykaisalami.geeruh.users.domain.models.UserId;
import pl.edu.pw.elka.paprykaisalami.geeruh.utils.DomainError;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Component
class IssueFacade {

    private final IssueService issueService;

    public List<IssueResponse> list() {
        var issues = issueService.list();
        return issues.stream()
                .map(IssueResponse::of)
                .collect(Collectors.toList());
    }

    public IssueResponse get(String rawIssueId) {
        var issue = issueService.get(IssueId.of(rawIssueId))
                .getOrElseThrow(DomainError::toException);
        return IssueResponse.of(issue);
    }

    public IssueResponse create(String projectCode, String statusCode, IssueRequest issueRequest) {
        var description = issueRequest.getDescription();
        var issue = issueService.create(
                new ProjectCode(projectCode),
                new StatusCode(statusCode),
                issueRequest.getType(),
                new Summary(issueRequest.getSummary()),
                new Description(description == null ? "" : description)
        ).getOrElseThrow(DomainError::toException);
        return IssueResponse.of(issue);
    }


    public IssueResponse update(String rawIssueId, IssueRequest issueRequest) {
        var issue = issueService.update(
                IssueId.of(rawIssueId),
                issueRequest.getType(),
                new Summary(issueRequest.getSummary()),
                new Description(issueRequest.getDescription())
        ).getOrElseThrow(DomainError::toException);
        return IssueResponse.of(issue);
    }

    public IssueResponse changeStatus(String rawIssueId, IssueChangeStatusRequest issueChangeStatusRequest) {
        var issue = issueService.changeStatus(
                IssueId.of(rawIssueId),
                new StatusCode(issueChangeStatusRequest.statusCode)
        ).getOrElseThrow(DomainError::toException);
        return IssueResponse.of(issue);
    }

    public IssueResponse assignUser(String rawIssueId, IssueAssignUserRequest issueAssignUserRequest) {
        var assigneeUserId = issueAssignUserRequest.assigneeUserId;
        var issue = issueService.assignUser(
                IssueId.of(rawIssueId),
                assigneeUserId == null ? null : new UserId(assigneeUserId)
        ).getOrElseThrow(DomainError::toException);
        return IssueResponse.of(issue);
    }

    public List<IssueHistoryResponse> getHistory(String rawIssueId) {
        var history = issueService.getHistory(IssueId.of(rawIssueId));
        return history.stream().map(IssueHistoryResponse::of).collect(Collectors.toList());
    }
}
