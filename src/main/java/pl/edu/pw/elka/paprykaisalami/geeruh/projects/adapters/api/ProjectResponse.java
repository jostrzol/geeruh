package pl.edu.pw.elka.paprykaisalami.geeruh.projects.adapters.api;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Value;
import pl.edu.pw.elka.paprykaisalami.geeruh.projects.domain.models.Project;

@Builder(access = AccessLevel.PRIVATE)
@Value
public class ProjectResponse {

    String code;

    String name;

    String description;

    static ProjectResponse of(Project project) {
        return ProjectResponse.builder()
                .code(project.getProjectCode().getValue())
                .name(project.getName())
                .description(project.getDescription())
                .build();
    }
}
