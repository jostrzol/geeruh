package pl.edu.pw.elka.paprykaisalami.geeruh.projects.adapters.persistent;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.envers.Audited;
import pl.edu.pw.elka.paprykaisalami.geeruh.issues.adapters.persistent.IssuePersistent;
import pl.edu.pw.elka.paprykaisalami.geeruh.projects.domain.models.Project;
import pl.edu.pw.elka.paprykaisalami.geeruh.projects.domain.models.ProjectCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

import static javax.persistence.CascadeType.REMOVE;

@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Audited
@Table(name = "Projects")
public class ProjectPersistent {

    @Id
    @Column(name = "code", updatable = false, nullable = false)
    private String code;

    @OneToMany(cascade = REMOVE, mappedBy = "project", fetch = FetchType.LAZY)
    private List<IssuePersistent> issues;

    private String name;

    private String description;

    ProjectPersistent(
            String code,
            String name,
            String description
    ) {
        this.code = code;
        this.name = name;
        this.description = description;
    }

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
