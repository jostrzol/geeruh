package pl.edu.pw.elka.paprykaisalami.geeruh.support.assertions;


import org.assertj.core.api.AbstractAssert;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.ObjectAssert;
import pl.edu.pw.elka.paprykaisalami.geeruh.projects.domain.models.Project;
import pl.edu.pw.elka.paprykaisalami.geeruh.projects.domain.models.ProjectCode;

import java.util.Objects;

public class ProjectAssert extends AbstractAssert<ProjectAssert, Project> {

    public ProjectAssert(Project actual) {
        super(actual, ProjectAssert.class);
    }

    public static ProjectAssert assertThat(Project actual) {
        return new ProjectAssert(actual);
    }

    public ProjectAssert hasCode(String projectCode) {
        isNotNull();

        Assertions.assertThat(this.actual)
                .hasFieldOrPropertyWithValue("projectCode", new ProjectCode(projectCode));

        return myself;
    }

    public ProjectAssert hasName(String name) {
        isNotNull();

        Assertions.assertThat(this.actual)
                .hasFieldOrPropertyWithValue("name", name);

        return myself;
    }

    public ProjectAssert hasDescription(String description) {
        isNotNull();

        Assertions.assertThat(this.actual)
                .hasFieldOrPropertyWithValue("description", description);

        return myself;
    }

    public ProjectAssert isEquivalentTo(Project expected) {
        isNotNull();

        Assertions.assertThat(actual)
                .usingRecursiveComparison()
                .isEqualTo(expected);

        return myself;
    }
}
