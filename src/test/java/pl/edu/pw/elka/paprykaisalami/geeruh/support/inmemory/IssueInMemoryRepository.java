package pl.edu.pw.elka.paprykaisalami.geeruh.support.inmemory;

import pl.edu.pw.elka.paprykaisalami.geeruh.issues.domain.models.Description;
import pl.edu.pw.elka.paprykaisalami.geeruh.issues.domain.models.Issue;
import pl.edu.pw.elka.paprykaisalami.geeruh.issues.domain.models.IssueHistoryEntry;
import pl.edu.pw.elka.paprykaisalami.geeruh.issues.domain.models.IssueId;
import pl.edu.pw.elka.paprykaisalami.geeruh.issues.domain.models.IssueType;
import pl.edu.pw.elka.paprykaisalami.geeruh.issues.domain.models.Summary;
import pl.edu.pw.elka.paprykaisalami.geeruh.issues.domain.ports.IssueRepository;
import pl.edu.pw.elka.paprykaisalami.geeruh.projects.domain.models.ProjectCode;
import pl.edu.pw.elka.paprykaisalami.geeruh.statuses.domain.models.StatusCode;
import pl.edu.pw.elka.paprykaisalami.geeruh.support.idgenerators.IdGenerator;
import pl.edu.pw.elka.paprykaisalami.geeruh.support.idgenerators.SequentialIntegerGenerator;

import java.util.List;

public class IssueInMemoryRepository extends BaseInMemoryRepository<Issue, IssueId> implements IssueRepository {

    IdGenerator<Integer> idGenerator = new SequentialIntegerGenerator();

    public IssueInMemoryRepository() {
        super(Issue.class, Issue::getIssueId);
    }

    @Override
    public Issue create(ProjectCode projectCode, StatusCode statusCode, IssueType type, Summary summary, Description description) {
        var issueId = new IssueId(projectCode, idGenerator.generateId());
        var issue = Issue.builder()
                .issueId(issueId)
                .type(type)
                .statusCode(statusCode)
                .summary(summary)
                .description(description)
                .build();
        return save(issue);
    }

    @Override
    public List<IssueHistoryEntry> getHistory(IssueId issueId) {
        throw new UnsupportedOperationException();
    }
}
