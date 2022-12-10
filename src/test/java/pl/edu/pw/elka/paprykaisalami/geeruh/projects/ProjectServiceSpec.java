package pl.edu.pw.elka.paprykaisalami.geeruh.projects;

import org.junit.jupiter.api.Test;
import pl.edu.pw.elka.paprykaisalami.geeruh.BaseSpec;
import pl.edu.pw.elka.paprykaisalami.geeruh.projects.domain.models.ProjectCode;
import pl.edu.pw.elka.paprykaisalami.geeruh.utils.DomainError.NotFoundDomainError;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.vavr.api.VavrAssertions.assertThat;
import static pl.edu.pw.elka.paprykaisalami.geeruh.support.ProjectAttributeDataset.*;
import static pl.edu.pw.elka.paprykaisalami.geeruh.support.assertions.GeeruhAssertions.assertThat;
import static pl.edu.pw.elka.paprykaisalami.geeruh.support.datasets.ProjectDataset.*;

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
        assertThat(project).isEquivalentTo(firstProject());
    }

    @Test
    void shouldGetProject() {
        // given
        thereAreProjects(firstProject());

        // when
        var project = projectService.get(new ProjectCode(FIRST_PROJECT_CODE));

        // expect
        assertThat(project).hasRightValueSatisfying(p -> assertThat(p)
                .isEquivalentTo(firstProject())
        );
    }

    @Test
    void shouldNotGetProject_whenAbsent() {
        // given
        thereAreProjects(firstProject());

        // when
        var project = projectService.get(new ProjectCode(SECOND_PROJECT_CODE));

        // expect
        assertThat(project).containsLeftInstanceOf(NotFoundDomainError.class);
    }

    @Test
    void shouldListAllProjects() {
        // given
        thereAreProjects(firstProject(), secondProject());

        // when
        var projects = projectService.list();

        // expect
        assertThat(projects)
                .usingRecursiveFieldByFieldElementComparator()
                .containsExactlyInAnyOrder(firstProject(), secondProject());
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
        thereAreProjects(firstProject());

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
