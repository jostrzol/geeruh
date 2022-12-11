package pl.edu.pw.elka.paprykaisalami.geeruh.projects;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import pl.edu.pw.elka.paprykaisalami.geeruh.BaseSpec;
import pl.edu.pw.elka.paprykaisalami.geeruh.projects.domain.models.ProjectCode;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ProjectCodeSpec extends BaseSpec {

    @ParameterizedTest
    @ValueSource(strings = {"AF", "APPLE"})
    void shouldProjectCodeBeValid(String projectCodeStr) {
        // given
        var projectCode = new ProjectCode(projectCodeStr);

        // when
        var violations = validator.validate(projectCode);

        // expect
        assertThat(violations).isEmpty();
    }


    @ParameterizedTest
    @ValueSource(strings = {"E", "BANANA", "ApPLE", "4PPLE", "APP|E"})
    void shouldProjectCodeBeInvalid(String projectCodeStr) {
        // given
        var projectCode = new ProjectCode(projectCodeStr);

        // when
        var violations = validator.validate(projectCode);

        // expect
        assertThat(violations).isNotEmpty();
    }
}
