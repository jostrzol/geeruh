package pl.edu.pw.elka.paprykaisalami.geeruh.projects.adapters.api;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.edu.pw.elka.paprykaisalami.geeruh.errors.ErrorsException;
import pl.edu.pw.elka.paprykaisalami.geeruh.projects.domain.models.ProjectCode;
import pl.edu.pw.elka.paprykaisalami.geeruh.projects.domain.ports.ProjectService;

import java.util.List;

@AllArgsConstructor
@Component
public class ProjectFacade {

    ProjectService projectService;

    public List<ProjectResponse> list() {
        return projectService.list().stream()
                .map(ProjectResponse::of)
                .toList();
    }

    public ProjectResponse get(String projectCode) {
        var project = projectService.get(ProjectCode.of(projectCode))
                .orElseThrow(() -> ErrorsException.notFound("project"));
        return ProjectResponse.of(project);
    }

    public ProjectResponse update(String projectCode, ProjectRequest projectRequest) {
        var project = projectService.create(
                ProjectCode.of(projectCode),
                projectRequest.getName(),
                projectRequest.getDescription()
        );
        return ProjectResponse.of(project);
    }

    public ProjectResponse create(String projectCode, ProjectRequest projectRequest) {
        var project = projectService.create(
                ProjectCode.of(projectCode),
                projectRequest.getName(),
                projectRequest.getDescription()
        );
        return ProjectResponse.of(project);
    }
}
