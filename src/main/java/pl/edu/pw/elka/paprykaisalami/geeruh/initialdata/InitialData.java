package pl.edu.pw.elka.paprykaisalami.geeruh.initialdata;


import lombok.AllArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import pl.edu.pw.elka.paprykaisalami.geeruh.issues.domain.models.Description;
import pl.edu.pw.elka.paprykaisalami.geeruh.issues.domain.models.IssueType;
import pl.edu.pw.elka.paprykaisalami.geeruh.issues.domain.models.Summary;
import pl.edu.pw.elka.paprykaisalami.geeruh.issues.domain.ports.IssueService;
import pl.edu.pw.elka.paprykaisalami.geeruh.projects.domain.models.ProjectCode;
import pl.edu.pw.elka.paprykaisalami.geeruh.projects.domain.ports.ProjectService;
import pl.edu.pw.elka.paprykaisalami.geeruh.statuses.domain.models.StatusCode;
import pl.edu.pw.elka.paprykaisalami.geeruh.statuses.domain.ports.StatusService;

@Component
@AllArgsConstructor
public class InitialData implements ApplicationRunner {

    private final IssueService issueService;

    private final ProjectService projectService;

    private final StatusService statusService;

    @Override
    public void run(ApplicationArguments args) {
        var firstProject = projectService.create(
                new ProjectCode("PIS"),
                "Strona sklepu 'Papryka i Salami'",
                "Projekt dotyczy strony internetowej tworzonej dla sklepu 'Papryka i Salami'"
        );

        var firstStatus = statusService.create(
                new StatusCode("OPEN"),
                "Open",
                1
        );

        var firstIssue = issueService.create(
                firstProject.getProjectCode(),
                firstStatus.getStatusCode(),
                IssueType.BUG,
                new Summary("Zmiana koloru guzika"),
                new Description("Guzik trzeba pilnie zmienić na zielony.")
        ).get();

        issueService.update(
                firstIssue.getIssueId(),
                firstStatus.getStatusCode(),
                IssueType.BUG,
                new Summary("Zmiana koloru guzików"),
                new Description("Guziki trzeba pilnie zmienić na zielony.")
        );

        var secondIssue = issueService.create(
                firstProject.getProjectCode(),
                firstStatus.getStatusCode(),
                IssueType.BUG,
                new Summary("Wycentrowanie logo"),
                new Description("Logo nie jest wycentrowane, proszę je wyśrodkować.")
        ).get();
        issueService.update(
                secondIssue.getIssueId(),
                firstStatus.getStatusCode(),
                IssueType.TASK,
                new Summary("Wycentrowanie logo"),
                new Description("Logo nie jest wycentrowane, proszę je wyśrodkować.")
        );
    }
}
