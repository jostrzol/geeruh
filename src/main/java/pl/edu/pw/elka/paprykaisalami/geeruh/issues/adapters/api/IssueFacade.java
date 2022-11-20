package pl.edu.pw.elka.paprykaisalami.geeruh.issues.adapters.api;

import lombok.AllArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import pl.edu.pw.elka.paprykaisalami.geeruh.errors.ErrorsException;
import pl.edu.pw.elka.paprykaisalami.geeruh.issues.domain.models.Description;
import pl.edu.pw.elka.paprykaisalami.geeruh.issues.domain.models.IssueId;
import pl.edu.pw.elka.paprykaisalami.geeruh.issues.domain.models.Summary;
import pl.edu.pw.elka.paprykaisalami.geeruh.issues.domain.ports.IssueService;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@AllArgsConstructor
@Component
class IssueFacade {

    private final IssueService issueService;

    public List<IssueResponse> list() {
        val issues = issueService.list();
        return issues.stream()
                .map(IssueResponse::of)
                .collect(Collectors.toList());
    }

    public IssueResponse get(UUID issueId) {
        val issue = issueService.get(IssueId.of(issueId))
                .orElseThrow(() -> ErrorsException.notFound("issue"));
        return IssueResponse.of(issue);
    }

    public IssueResponse create(IssueRequest issueRequest) {
        val description = issueRequest.getDescription();
        val issue = issueService.create(
                issueRequest.getType(),
                Summary.of(issueRequest.getSummary()),
                description == null ? null : Description.of(description)
        );
        return IssueResponse.of(issue);
    }


    public IssueResponse update(UUID issueId, IssueRequest issueRequest) {
        val description = issueRequest.getDescription();
        val issue = issueService.update(
                IssueId.of(issueId),
                issueRequest.getType(),
                Summary.of(issueRequest.getSummary()),
                description == null ? null : Description.of(description)
        ).orElseThrow(() -> ErrorsException.notFound("issue"));;
        return IssueResponse.of(issue);
    }

    public List<IssueHistoryResponse> getHistory(UUID issueId) {
        val history = issueService.getHistory(IssueId.of(issueId));
        return history.stream().map(IssueHistoryResponse::of).collect(Collectors.toList());
    }
}
