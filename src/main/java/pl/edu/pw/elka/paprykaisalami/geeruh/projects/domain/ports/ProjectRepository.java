package pl.edu.pw.elka.paprykaisalami.geeruh.projects.domain.ports;


import io.vavr.control.Either;
import pl.edu.pw.elka.paprykaisalami.geeruh.projects.domain.models.Project;
import pl.edu.pw.elka.paprykaisalami.geeruh.projects.domain.models.ProjectCode;
import pl.edu.pw.elka.paprykaisalami.geeruh.utils.DomainError;

import java.util.List;
import java.util.Optional;

public interface ProjectRepository {

    List<Project> findAll();

    Either<DomainError, Project> findByCode(ProjectCode projectCode);

    Project save(Project project);
}
