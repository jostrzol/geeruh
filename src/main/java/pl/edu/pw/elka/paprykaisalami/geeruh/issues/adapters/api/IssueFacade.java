package pl.edu.pw.elka.paprykaisalami.geeruh.issues.adapters.api;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.edu.pw.elka.paprykaisalami.geeruh.errors.ErrorsException;
import pl.edu.pw.elka.paprykaisalami.geeruh.issues.domain.models.Description;
import pl.edu.pw.elka.paprykaisalami.geeruh.issues.domain.models.IssueId;
import pl.edu.pw.elka.paprykaisalami.geeruh.issues.domain.models.Summary;
import pl.edu.pw.elka.paprykaisalami.geeruh.issues.domain.ports.IssueService;
import pl.edu.pw.elka.paprykaisalami.geeruh.projects.domain.models.ProjectCode;
import pl.edu.pw.elka.paprykaisalami.geeruh.statuses.domain.models.StatusCode;
import pl.edu.pw.elka.paprykaisalami.geeruh.utils.DomainError;

import java.util.List;
import java.util.Optional;
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
        var issue = issueService.get(parseIssueId(rawIssueId))
                .getOrElseThrow(DomainError::toException);
        return IssueResponse.of(issue);
    }

    public IssueResponse create(String projectCode, IssueRequest issueRequest) {
        var description = issueRequest.getDescription();
        var issue = issueService.create(
                new ProjectCode(projectCode),
                new StatusCode(issueRequest.getStatusCode()),
                issueRequest.getType(),
                new Summary(issueRequest.getSummary()),
                new Description(description == null ? "" : description)
        ).getOrElseThrow(DomainError::toException);
        return IssueResponse.of(issue);
    }


    public IssueResponse update(String rawIssueId, IssueRequest issueRequest) {
        var issue = issueService.update(
                parseIssueId(rawIssueId),
                new StatusCode(issueRequest.statusCode),
                issueRequest.getType(),
                new Summary(issueRequest.getSummary()),
                new Description(issueRequest.getDescription())
        ).getOrElseThrow(DomainError::toException);
        return IssueResponse.of(issue);
    }

    public List<IssueHistoryResponse> getHistory(String rawIssueId) {
        var history = issueService.getHistory(parseIssueId(rawIssueId));
        return history.stream().map(IssueHistoryResponse::of).collect(Collectors.toList());
    }

    private IssueId parseIssueId(String text) {
        var parts = text.split("-", 2);

        var projectCode = parts[0];
        var issueNumber = Integer.parseUnsignedInt(parts[1]);

        return new IssueId(new ProjectCode(projectCode), issueNumber);
    }
}
