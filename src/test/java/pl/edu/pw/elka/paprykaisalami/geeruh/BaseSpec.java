package pl.edu.pw.elka.paprykaisalami.geeruh;

import org.hibernate.validator.internal.engine.ValidatorImpl;
import pl.edu.pw.elka.paprykaisalami.geeruh.issues.domain.ports.IssueRepository;
import pl.edu.pw.elka.paprykaisalami.geeruh.issues.domain.ports.IssueService;
import pl.edu.pw.elka.paprykaisalami.geeruh.projects.domain.ports.ProjectRepository;
import pl.edu.pw.elka.paprykaisalami.geeruh.projects.domain.ports.ProjectService;
import pl.edu.pw.elka.paprykaisalami.geeruh.support.inmemory.IssueInMemoryRepository;
import pl.edu.pw.elka.paprykaisalami.geeruh.support.inmemory.ProjectInMemoryRepository;

import javax.validation.Validation;
import javax.validation.Validator;

public abstract class BaseSpec {

    protected final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    protected final ProjectRepository projectRepository = new ProjectInMemoryRepository();
    protected final ProjectService projectService = new ProjectService(projectRepository);

    protected final IssueRepository issueRepository = new IssueInMemoryRepository();
    protected final IssueService issueService = new IssueService(projectService, issueRepository);
}
