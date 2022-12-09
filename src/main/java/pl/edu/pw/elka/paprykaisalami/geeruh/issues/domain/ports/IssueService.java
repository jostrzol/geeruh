package pl.edu.pw.elka.paprykaisalami.geeruh.issues.domain.ports;

import io.vavr.control.Either;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.edu.pw.elka.paprykaisalami.geeruh.issues.domain.models.Description;
import pl.edu.pw.elka.paprykaisalami.geeruh.issues.domain.models.Issue;
import pl.edu.pw.elka.paprykaisalami.geeruh.issues.domain.models.IssueHistoryEntry;
import pl.edu.pw.elka.paprykaisalami.geeruh.issues.domain.models.IssueId;
import pl.edu.pw.elka.paprykaisalami.geeruh.issues.domain.models.IssueType;
import pl.edu.pw.elka.paprykaisalami.geeruh.issues.domain.models.Summary;
import pl.edu.pw.elka.paprykaisalami.geeruh.projects.domain.models.ProjectCode;
import pl.edu.pw.elka.paprykaisalami.geeruh.projects.domain.ports.ProjectService;
import pl.edu.pw.elka.paprykaisalami.geeruh.utils.DomainError;

import javax.transaction.Transactional;
import java.util.List;

@AllArgsConstructor
@Component
@Transactional
public class IssueService {

    private final ProjectService projectService;

    private final IssueRepository issueRepository;

    public List<Issue> list() {
        return issueRepository.findAll();
    }

    public Either<DomainError, Issue> get(IssueId issueId) {
        return issueRepository.findById(issueId);
    }

    public Either<DomainError, Issue> create(ProjectCode projectCode, IssueType type, Summary summary, Description description) {
        return projectService.get(projectCode)
                .map(p -> issueRepository.create(projectCode, type, summary, description));
    }

    public Either<DomainError, Issue> update(IssueId issueId, IssueType type, Summary summary, Description description) {
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
}
