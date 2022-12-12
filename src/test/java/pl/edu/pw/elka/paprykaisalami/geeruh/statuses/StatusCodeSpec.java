package pl.edu.pw.elka.paprykaisalami.geeruh.statuses;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import pl.edu.pw.elka.paprykaisalami.geeruh.BaseSpec;
import pl.edu.pw.elka.paprykaisalami.geeruh.statuses.domain.models.StatusCode;

import static org.assertj.core.api.Assertions.assertThat;

public class StatusCodeSpec extends BaseSpec {

    @ParameterizedTest
    @ValueSource(strings = {"OP", "OPEN", "CLOSED"})
    void shouldStatusCodeBeValid(String statusCodeStr) {
        // given
        var statusCode = new StatusCode(statusCodeStr);

        // when
        var violations = validator.validate(statusCode);

        // expect
        assertThat(violations).isEmpty();
    }


    @ParameterizedTest
    @ValueSource(strings = {"E", "PSEUDOPSEUDOHYPOPARATHYROIDISM", "ApPLE", "4PPLE", "APP|E"})
    void shouldStatusCodeBeInvalid(String statusCodeStr) {
        // given
        var statusCode = new StatusCode(statusCodeStr);

        // when
        var violations = validator.validate(statusCode);

        // expect
        assertThat(violations).isNotEmpty();
    }
}
