package pl.edu.pw.elka.paprykaisalami.geeruh;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.edu.pw.elka.paprykaisalami.geeruh.issues.domain.models.Description;
import pl.edu.pw.elka.paprykaisalami.geeruh.issues.domain.models.IssueType;
import pl.edu.pw.elka.paprykaisalami.geeruh.issues.domain.models.Summary;
import pl.edu.pw.elka.paprykaisalami.geeruh.issues.domain.ports.IssueService;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class IssuesPersistentTests {

    @Autowired
    private IssueService issueService;

    @Test
    void CreateIssue() {
        var issue = issueService.create(IssueType.BUG, Summary.of("summary"), Description.of("desc"));

        var issueFromGet = issueService.get(issue.getIssueId()).get();

        assertThat(issue).isEqualTo(issueFromGet);
    }

    @Test
    void ListIssue() {
        var issue = issueService.create(IssueType.BUG, Summary.of("summary"), Description.of("desc"));

        var issueList = issueService.list();

        assertThat(issueList).contains(issue);
    }

    @Test
    void UpdateIssue() {
        var issue = issueService.create(IssueType.BUG, Summary.of("summary"), Description.of("desc"));

        var issueUpdated = issueService.update(issue.getIssueId(), IssueType.BUG, Summary.of("summary2"), Description.of("desc2")).get();

        var issueUpdatedFromGet = issueService.get(issue.getIssueId()).get();
        assertThat(issueUpdated).isEqualTo(issueUpdatedFromGet);
        assertThat(issueUpdated.getIssueId()).isEqualTo(issue.getIssueId());
        assertThat(issueUpdated.getSummary().getValue()).isEqualTo("summary2");
    }

    @Test
    void IssueHistory() {
        var issue = issueService.create(IssueType.BUG, Summary.of("summary"), Description.of("desc"));
        issueService.update(issue.getIssueId(), IssueType.BUG, Summary.of("summary2"), Description.of("desc2")).get();

        var history = issueService.getHistory(issue.getIssueId()).get();

        assertThat(history).anyMatch(h-> h.getHistoricIssue().getSummary().getValue().equals("summary"));
        assertThat(history).anyMatch(h-> h.getHistoricIssue().getSummary().getValue().equals("summary2"));
    }

}
