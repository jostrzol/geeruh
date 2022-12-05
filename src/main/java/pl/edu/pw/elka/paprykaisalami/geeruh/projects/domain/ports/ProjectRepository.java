package pl.edu.pw.elka.paprykaisalami.geeruh.projects.domain.ports;


import pl.edu.pw.elka.paprykaisalami.geeruh.projects.domain.models.Project;
import pl.edu.pw.elka.paprykaisalami.geeruh.projects.domain.models.ProjectCode;

import java.util.List;
import java.util.Optional;

public interface ProjectRepository {

    List<Project> findAll();

    Optional<Project> findByCode(ProjectCode projectCode);

    Project save(Project project);
}
