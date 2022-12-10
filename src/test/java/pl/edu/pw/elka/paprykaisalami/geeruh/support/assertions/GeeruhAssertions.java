package pl.edu.pw.elka.paprykaisalami.geeruh.support.assertions;

import pl.edu.pw.elka.paprykaisalami.geeruh.projects.domain.models.Project;

public class GeeruhAssertions {

    public static ProjectAssert assertThat(Project actual) {
        return new ProjectAssert(actual);
    }
}
