package pl.edu.pw.elka.paprykaisalami.geeruh.issues.domain.ports;

import io.vavr.control.Either;
import lombok.AllArgsConstructor;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.pw.elka.paprykaisalami.geeruh.issues.domain.models.Description;
import pl.edu.pw.elka.paprykaisalami.geeruh.issues.domain.models.Issue;
import pl.edu.pw.elka.paprykaisalami.geeruh.issues.domain.models.IssueHistoryEntry;
import pl.edu.pw.elka.paprykaisalami.geeruh.issues.domain.models.IssueId;
import pl.edu.pw.elka.paprykaisalami.geeruh.issues.domain.models.IssueType;
import pl.edu.pw.elka.paprykaisalami.geeruh.issues.domain.models.Summary;
import pl.edu.pw.elka.paprykaisalami.geeruh.projects.domain.models.ProjectCode;
import pl.edu.pw.elka.paprykaisalami.geeruh.projects.domain.ports.ProjectService;
import pl.edu.pw.elka.paprykaisalami.geeruh.statuses.domain.models.StatusCode;
import pl.edu.pw.elka.paprykaisalami.geeruh.statuses.domain.ports.StatusService;
import pl.edu.pw.elka.paprykaisalami.geeruh.users.domain.models.UserId;
import pl.edu.pw.elka.paprykaisalami.geeruh.users.domain.ports.UserService;
import pl.edu.pw.elka.paprykaisalami.geeruh.utils.DomainError;

import javax.validation.Valid;
import java.util.List;

@Validated
@AllArgsConstructor
@Component
@Transactional(readOnly = true)
public class IssueService {

    private final ProjectService projectService;

    private final StatusService statusService;

    private final UserService userService;

    private final IssueRepository issueRepository;


    public List<Issue> list() {
        return issueRepository.findAll();
    }


    public Either<DomainError, Issue> get(IssueId issueId) {
        return issueRepository.findById(issueId);
    }

    @Valid
    @Transactional
    public Either<DomainError, Issue> create(
            ProjectCode projectCode,
            StatusCode statusCode,
            IssueType type,
            Summary summary,
            Description description
    ) {
        var status = statusService.get(statusCode);
        if (status.isLeft()) {
            return Either.left(status.getLeft());
        }
        return projectService.get(projectCode)
                .map(p -> issueRepository.create(projectCode, statusCode, type, summary, description));
    }

    @Valid
    @Transactional
    public Either<DomainError, Issue> update(
            IssueId issueId,
            IssueType type,
            Summary summary,
            Description description
    ) {
        return issueRepository.findById(issueId).map(
                issue -> {
                    issue.setType(type);
                    issue.setSummary(summary);
                    issue.setDescription(description);
                    return issueRepository.save(issue);
                });
    }

    public List<IssueHistoryEntry> getHistory(IssueId issueId) {
        return issueRepository.getHistory(issueId);
    }

    @Transactional
    @Valid
    public Either<DomainError, Issue> changeStatus(IssueId issueId, StatusCode statusCode) {
        var status = statusService.get(statusCode);
        if (status.isLeft()) {
            return Either.left(status.getLeft());
        }
        return issueRepository.findById(issueId).map(
                issue -> {
                    issue.setStatusCode(statusCode);
                    return issueRepository.save(issue);
                });
    }

    @Transactional
    @Valid
    public Either<DomainError, Issue> assignUser(IssueId issueId, @Nullable UserId userId) {
        if (userId != null) {
            var user = userService.get(userId);
            if (user.isLeft()) {
                return Either.left(user.getLeft());
            }
        }
        return issueRepository.findById(issueId).map(
                issue -> {
                    issue.setAssigneeUserId(userId);
                    return issueRepository.save(issue);
                });
    }

    @Transactional
    @Valid
    public Either<DomainError, Issue> relateIssue(IssueId issueId, IssueId relatedIssueId) {
        return issueRepository.findById(issueId).map(
                issue -> {
                    issue.setRelatedIssueId(relatedIssueId);
                    return issueRepository.save(issue);
                });
    }

}
