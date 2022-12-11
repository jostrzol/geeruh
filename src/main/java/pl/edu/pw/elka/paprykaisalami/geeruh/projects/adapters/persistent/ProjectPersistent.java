package pl.edu.pw.elka.paprykaisalami.geeruh.projects.adapters.persistent;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import pl.edu.pw.elka.paprykaisalami.geeruh.projects.domain.models.Project;
import pl.edu.pw.elka.paprykaisalami.geeruh.projects.domain.models.ProjectCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "Projects")
public class ProjectPersistent {

    @Id
    @Column(name = "code", updatable = false, nullable = false)
    private String code;

    private String name;

    private String description;

    public Project toProject() {
        return Project.builder()
                .projectCode(new ProjectCode(code))
                .name(name)
                .description(description)
                .build();
    }

    public static ProjectPersistent of(Project project) {
        return new ProjectPersistent(
                project.getProjectCode().value(),
                project.getName(),
                project.getDescription()
        );
    }
}
