package pl.edu.pw.elka.paprykaisalami.geeruh.issues.domain.ports;


import io.vavr.control.Either;
import pl.edu.pw.elka.paprykaisalami.geeruh.issues.domain.models.Issue;
import pl.edu.pw.elka.paprykaisalami.geeruh.issues.domain.models.Description;
import pl.edu.pw.elka.paprykaisalami.geeruh.issues.domain.models.IssueHistoryEntry;
import pl.edu.pw.elka.paprykaisalami.geeruh.issues.domain.models.IssueId;
import pl.edu.pw.elka.paprykaisalami.geeruh.issues.domain.models.IssueType;
import pl.edu.pw.elka.paprykaisalami.geeruh.issues.domain.models.Summary;
import pl.edu.pw.elka.paprykaisalami.geeruh.projects.domain.models.ProjectCode;
import pl.edu.pw.elka.paprykaisalami.geeruh.statuses.domain.models.StatusCode;
import pl.edu.pw.elka.paprykaisalami.geeruh.utils.DomainError;

import java.util.List;

public interface IssueRepository {

    List<Issue> findAll();

    Either<DomainError, Issue> findById(IssueId issueId);

    Issue create(ProjectCode projectCode, StatusCode statusCode, IssueType type, Summary summary, Description description);

    Issue save(Issue issue);

    List<IssueHistoryEntry> getHistory(IssueId issueId);
}
