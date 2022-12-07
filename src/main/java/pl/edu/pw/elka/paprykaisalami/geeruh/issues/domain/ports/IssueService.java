package pl.edu.pw.elka.paprykaisalami.geeruh.issues.domain.ports;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.edu.pw.elka.paprykaisalami.geeruh.issues.domain.models.Issue;
import pl.edu.pw.elka.paprykaisalami.geeruh.issues.domain.models.Description;
import pl.edu.pw.elka.paprykaisalami.geeruh.issues.domain.models.IssueHistoryEntry;
import pl.edu.pw.elka.paprykaisalami.geeruh.issues.domain.models.IssueId;
import pl.edu.pw.elka.paprykaisalami.geeruh.issues.domain.models.IssueType;
import pl.edu.pw.elka.paprykaisalami.geeruh.issues.domain.models.Summary;
import pl.edu.pw.elka.paprykaisalami.geeruh.projects.domain.models.ProjectCode;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Component
public class IssueService {

    private final IssueRepository issueRepository;

    public List<Issue> list() {
        return issueRepository.findAll();
    }

    public Optional<Issue> get(IssueId issueId) {
        return issueRepository.findById(issueId);
    }

    public Issue create(ProjectCode projectCode, IssueType type, Summary summary, Description description) {
        return issueRepository.create(projectCode, type, summary, description);
    }

    public Optional<Issue> update(IssueId issueId, IssueType type, Summary summary, Description description) {
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
