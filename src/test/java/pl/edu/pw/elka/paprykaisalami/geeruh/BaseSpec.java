package pl.edu.pw.elka.paprykaisalami.geeruh;

import org.hibernate.validator.internal.engine.ValidatorImpl;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.util.SerializationUtils;
import pl.edu.pw.elka.paprykaisalami.geeruh.issues.domain.models.Issue;
import pl.edu.pw.elka.paprykaisalami.geeruh.issues.domain.ports.IssueRepository;
import pl.edu.pw.elka.paprykaisalami.geeruh.issues.domain.ports.IssueService;
import pl.edu.pw.elka.paprykaisalami.geeruh.projects.domain.models.Project;
import pl.edu.pw.elka.paprykaisalami.geeruh.projects.domain.ports.ProjectRepository;
import pl.edu.pw.elka.paprykaisalami.geeruh.projects.domain.ports.ProjectService;
import pl.edu.pw.elka.paprykaisalami.geeruh.support.inmemory.IssueInMemoryRepository;
import pl.edu.pw.elka.paprykaisalami.geeruh.support.inmemory.ProjectInMemoryRepository;

import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Arrays;

public abstract class BaseSpec {

    protected final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    protected ProjectRepository projectRepository;
    protected ProjectService projectService;

    protected IssueRepository issueRepository;
    protected IssueService issueService;

    @BeforeEach
    protected void initialize() {
        projectRepository = new ProjectInMemoryRepository();
        projectService = new ProjectService(projectRepository);

        issueRepository = new IssueInMemoryRepository();
        issueService = new IssueService(projectService, issueRepository);
    }

    protected void thereAreProjects(Project... projects) {
        Arrays.stream(projects)
                .forEach(projectRepository::save);
    }

    protected void thereAreIssues(Issue... issues) {
        Arrays.stream(issues).forEach(issueRepository::save);
    }
}
