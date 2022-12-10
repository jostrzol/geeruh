package pl.edu.pw.elka.paprykaisalami.geeruh.support.assertions;

import org.assertj.core.api.InstanceOfAssertFactory;
import pl.edu.pw.elka.paprykaisalami.geeruh.projects.domain.models.Project;

public class GeeruhInstanceOfAssertFactories {

    public static final InstanceOfAssertFactory<Project, ProjectAssert> PROJECT = new InstanceOfAssertFactory<>(
            Project.class,
            GeeruhAssertions::assertThat
    );
}
