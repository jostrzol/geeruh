package pl.edu.pw.elka.paprykaisalami.geeruh.issues.domain.ports;


import pl.edu.pw.elka.paprykaisalami.geeruh.issues.domain.models.Issue;
import pl.edu.pw.elka.paprykaisalami.geeruh.issues.domain.models.Description;
import pl.edu.pw.elka.paprykaisalami.geeruh.issues.domain.models.IssueHistoryEntry;
import pl.edu.pw.elka.paprykaisalami.geeruh.issues.domain.models.IssueId;
import pl.edu.pw.elka.paprykaisalami.geeruh.issues.domain.models.IssueType;
import pl.edu.pw.elka.paprykaisalami.geeruh.issues.domain.models.Summary;
import pl.edu.pw.elka.paprykaisalami.geeruh.projects.domain.models.ProjectCode;

import java.util.List;
import java.util.Optional;

public interface IssueRepository {

    List<Issue> findAll();

    Optional<Issue> findById(IssueId issueId);

    Issue create(ProjectCode projectCode, IssueType type, Summary summary, Description description);

    Issue save(Issue issue);

    List<IssueHistoryEntry> getHistory(IssueId issueId);
}
