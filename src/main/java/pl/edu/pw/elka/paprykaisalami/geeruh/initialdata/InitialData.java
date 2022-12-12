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

        var openStatus = statusService.create(
                new StatusCode("OPEN"),
                "Open",
                1
        );

        var inProgressStatus = statusService.create(
                new StatusCode("INP"),
                "In Progress",
                2
        );

        var closedStatus = statusService.create(
                new StatusCode("CLOSED"),
                "Closed",
                3
        );

        var firstIssue = issueService.create(
                firstProject.getProjectCode(),
                openStatus.getStatusCode(),
                IssueType.BUG,
                new Summary("Zmiana koloru guzika"),
                new Description("Guzik trzeba pilnie zmienić na zielony.")
        ).get();

        issueService.update(
                firstIssue.getIssueId(),
                openStatus.getStatusCode(),
                IssueType.BUG,
                new Summary("Zmiana koloru guzików"),
                new Description("Guziki trzeba pilnie zmienić na zielony.")
        );
        var secondIssue = issueService.create(
                firstProject.getProjectCode(),
                openStatus.getStatusCode(),
                IssueType.BUG,
                new Summary("Wycentrowanie logo"),
                new Description("Logo nie jest wycentrowane, proszę je wyśrodkować.")
        ).get();
        issueService.update(
                secondIssue.getIssueId(),
                inProgressStatus.getStatusCode(),
                IssueType.TASK,
                new Summary("Wycentrowanie logo"),
                new Description("Logo nie jest wycentrowane, proszę je wyśrodkować.")
        );
        issueService.update(
                secondIssue.getIssueId(),
                closedStatus.getStatusCode(),
                IssueType.TASK,
                new Summary("Wycentrowanie logo"),
                new Description("Logo nie jest wycentrowane, proszę je wyśrodkować.")
        );
    }
}
