package pl.edu.pw.elka.paprykaisalami.geeruh.projects.adapters.api;

import pl.edu.pw.elka.paprykaisalami.geeruh.projects.domain.models.Project;

public record ProjectResponse(String code, String name, String description) {

    static ProjectResponse of(Project project) {
        return new ProjectResponse(
                project.getProjectCode().value(),
                project.getName(),
                project.getDescription()
        );
    }
}
