package pl.edu.pw.elka.paprykaisalami.geeruh.spring.initialdata;


import lombok.AllArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import pl.edu.pw.elka.paprykaisalami.geeruh.comments.domain.ports.CommentService;
import pl.edu.pw.elka.paprykaisalami.geeruh.issues.domain.models.Description;
import pl.edu.pw.elka.paprykaisalami.geeruh.issues.domain.models.IssueType;
import pl.edu.pw.elka.paprykaisalami.geeruh.issues.domain.models.Summary;
import pl.edu.pw.elka.paprykaisalami.geeruh.issues.domain.ports.IssueService;
import pl.edu.pw.elka.paprykaisalami.geeruh.projects.domain.models.ProjectCode;
import pl.edu.pw.elka.paprykaisalami.geeruh.projects.domain.ports.ProjectService;
import pl.edu.pw.elka.paprykaisalami.geeruh.statuses.domain.models.StatusCode;
import pl.edu.pw.elka.paprykaisalami.geeruh.statuses.domain.ports.StatusService;
import pl.edu.pw.elka.paprykaisalami.geeruh.users.domain.ports.UserService;

@Profile("!prod")
@Component
@AllArgsConstructor
public class InitialData implements ApplicationRunner {

    private final IssueService issueService;

    private final ProjectService projectService;

    private final StatusService statusService;

    private final UserService userService;

    private final CommentService commentService;

    @Override
    public void run(ApplicationArguments args) {
        var user = userService.create("user",
                "password",
                "user@mail.com",
                "First",
                null,
                "User");
        var admin = userService.create("admin",
                "password",
                "admin@mail.com",
                "First",
                null,
                "Admin");

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
                IssueType.TASK,
                new Summary("Wycentrowanie logo"),
                new Description("Logo nie jest wycentrowane, proszę je wyśrodkować.")
        );
        issueService.update(
                secondIssue.getIssueId(),
                IssueType.TASK,
                new Summary("Wycentrowanie logo"),
                new Description("Logo nie jest wycentrowane, proszę je wyśrodkować.")
        );

        var thirdIssue = issueService.create(
                firstProject.getProjectCode(),
                openStatus.getStatusCode(),
                IssueType.BUG,
                new Summary("Zablokowanie portu na PostgreSQL"),
                new Description("Kolejny raz zainstalowali nam koparkę na produkcyjnej maszynie. Może zablokujmy port na DB?")
        ).get();

        issueService.relateIssue(firstIssue.getIssueId(), secondIssue.getIssueId());
        issueService.relateIssue(firstIssue.getIssueId(), thirdIssue.getIssueId());

        var firstComment = commentService.create(
                firstIssue.getIssueId(),
                user.getLogin(),
                "Bardzo nietuzinkowy problem."
        ).get();
        commentService.update(
                firstComment.getCommentId(),
                "Bardzo tuzinkowy problem."
        );
        commentService.create(
                firstIssue.getIssueId(),
                admin.getLogin(),
                "Zgłoszenie narusza paragraf 3 regulaminu projektu."
        );
    }
}
