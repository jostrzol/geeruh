package pl.edu.pw.elka.paprykaisalami.geeruh.projects;

import org.junit.jupiter.api.Test;
import pl.edu.pw.elka.paprykaisalami.geeruh.BaseSpec;
import pl.edu.pw.elka.paprykaisalami.geeruh.projects.domain.models.ProjectCode;
import pl.edu.pw.elka.paprykaisalami.geeruh.utils.DomainError.NotFoundDomainError;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.vavr.api.VavrAssertions.assertThat;
import static pl.edu.pw.elka.paprykaisalami.geeruh.support.ProjectAttributeDataset.*;
import static pl.edu.pw.elka.paprykaisalami.geeruh.support.ProjectDataset.FIRST_PROJECT;
import static pl.edu.pw.elka.paprykaisalami.geeruh.support.assertions.GeeruhAssertions.assertThat;

public class ProjectServiceSpec extends BaseSpec {

    @Test
    void shouldCreateProject() {
        // given
        var project = projectService.create(
                new ProjectCode(FIRST_PROJECT_CODE),
                FIRST_PROJECT_NAME,
                FIRST_PROJECT_DESCRIPTION
        );

        // expect
        assertThat(project).isEquivalentTo(FIRST_PROJECT);
    }

    @Test
    void shouldGetProject() {
        // given
        var createdProject = projectService.create(
                new ProjectCode(FIRST_PROJECT_CODE),
                FIRST_PROJECT_NAME,
                FIRST_PROJECT_DESCRIPTION
        );

        // when
        var project = projectService.get(new ProjectCode(FIRST_PROJECT_CODE));

        // expect
        assertThat(project).hasRightValueSatisfying(p -> assertThat(p)
                .isEquivalentTo(createdProject)
        );
    }

    @Test
    void shouldNotGetProject_whenAbsent() {
        // given
        var createdProject = projectService.create(
                new ProjectCode(FIRST_PROJECT_CODE),
                FIRST_PROJECT_NAME,
                FIRST_PROJECT_DESCRIPTION
        );

        // when
        var project = projectService.get(new ProjectCode(SECOND_PROJECT_CODE));

        // expect
        assertThat(project).containsLeftInstanceOf(NotFoundDomainError.class);
    }

    @Test
    void shouldListAllProjects() {
        // given
        var project1 = projectService.create(
                new ProjectCode(FIRST_PROJECT_CODE),
                FIRST_PROJECT_NAME,
                FIRST_PROJECT_DESCRIPTION
        );

        // and
        var project2 = projectService.create(
                new ProjectCode(SECOND_PROJECT_CODE),
                SECOND_PROJECT_NAME,
                SECOND_PROJECT_DESCRIPTION
        );

        // when
        var projects = projectService.list();

        // expect
        assertThat(projects)
                .usingRecursiveFieldByFieldElementComparator()
                .containsExactlyInAnyOrder(project1, project2);
    }

    @Test
    void shouldListNoProjects_whenNoneCreated() {
        // given
        var projects = projectService.list();

        // expect
        assertThat(projects).isEmpty();
    }

    @Test
    void shouldUpdateProject() {
        // given
        projectService.create(
                new ProjectCode(FIRST_PROJECT_CODE),
                FIRST_PROJECT_NAME,
                FIRST_PROJECT_DESCRIPTION
        );

        // when
        var project = projectService.update(
                new ProjectCode(FIRST_PROJECT_CODE),
                SECOND_PROJECT_NAME,
                FIRST_PROJECT_DESCRIPTION
        );

        // expect
        assertThat(project).hasRightValueSatisfying(p -> assertThat(p)
                .hasCode(FIRST_PROJECT_CODE)
                .hasName(SECOND_PROJECT_NAME)
                .hasDescription(FIRST_PROJECT_DESCRIPTION)
        );
    }

    @Test
    void shouldNotUpdateProject_whenAbsent() {
        // given
        var project = projectService.update(
                new ProjectCode(FIRST_PROJECT_CODE),
                SECOND_PROJECT_NAME,
                FIRST_PROJECT_DESCRIPTION
        );

        // expect
        assertThat(project).containsLeftInstanceOf(NotFoundDomainError.class);
    }
}
