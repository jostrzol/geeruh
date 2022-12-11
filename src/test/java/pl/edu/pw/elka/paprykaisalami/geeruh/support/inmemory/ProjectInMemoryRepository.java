package pl.edu.pw.elka.paprykaisalami.geeruh.support.inmemory;

import io.vavr.control.Either;
import pl.edu.pw.elka.paprykaisalami.geeruh.projects.domain.models.Project;
import pl.edu.pw.elka.paprykaisalami.geeruh.projects.domain.models.ProjectCode;
import pl.edu.pw.elka.paprykaisalami.geeruh.projects.domain.ports.ProjectRepository;
import pl.edu.pw.elka.paprykaisalami.geeruh.utils.DomainError;

public class ProjectInMemoryRepository extends BaseInMemoryRepository<Project, ProjectCode> implements ProjectRepository {

    public ProjectInMemoryRepository() {
        super(Project.class, Project::getProjectCode);
    }

    @Override
    public Either<DomainError, Project> findByCode(ProjectCode projectCode) {
        return findById(projectCode);
    }
}
