package pl.edu.pw.elka.paprykaisalami.geeruh.projects.domain.ports;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.edu.pw.elka.paprykaisalami.geeruh.projects.domain.models.Project;
import pl.edu.pw.elka.paprykaisalami.geeruh.projects.domain.models.ProjectCode;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Component
public class ProjectService {

    ProjectRepository projectRepository;

    public List<Project> list() {
        return projectRepository.findAll();
    }

    public Optional<Project> get(ProjectCode projectCode) {
        return projectRepository.findByCode(projectCode);
    }

    public Project create(ProjectCode projectCode, String name, String description) {
        var project = Project.builder()
                .projectCode(projectCode)
                .name(name)
                .description(description)
                .build();
        return projectRepository.save(project);
    }

    public Optional<Project> update(ProjectCode projectCode, String name, String description) {
        return projectRepository.findByCode(projectCode).map(
                project -> {
                    project.setName(name);
                    project.setDescription(description);
                    return projectRepository.save(project);
                });
    }

}
