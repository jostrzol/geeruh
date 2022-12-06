package pl.edu.pw.elka.paprykaisalami.geeruh.initialdata;


import lombok.AllArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import pl.edu.pw.elka.paprykaisalami.geeruh.issues.domain.models.Description;
import pl.edu.pw.elka.paprykaisalami.geeruh.issues.domain.models.IssueType;
import pl.edu.pw.elka.paprykaisalami.geeruh.issues.domain.models.Summary;
import pl.edu.pw.elka.paprykaisalami.geeruh.issues.domain.ports.IssueService;

@Component
@AllArgsConstructor
public class InitialData implements ApplicationRunner {

    private final IssueService issueService;

    @Override
    public void run(ApplicationArguments args) {
        var firstIssue = issueService.create(
                IssueType.BUG,
                Summary.of("Zmiana koloru guzika"),
                Description.of("Guzik trzeba pilnie zmienić na zielony.")
        );
        issueService.update(
                firstIssue.getIssueId(),
                IssueType.BUG,
                Summary.of("Zmiana koloru guzików"),
                Description.of("Guziki trzeba pilnie zmienić na zielony.")
        );

        var secondIssue = issueService.create(
                IssueType.BUG,
                Summary.of("Wycentrowanie logo"),
                Description.of("Logo nie jest wycentrowane, proszę je wyśrodkować.")
        );
        issueService.update(
                secondIssue.getIssueId(),
                IssueType.TASK,
                Summary.of("Wycentrowanie logo"),
                Description.of("Logo nie jest wycentrowane, proszę je wyśrodkować.")
        );
    }
}
