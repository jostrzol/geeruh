package pl.edu.pw.elka.paprykaisalami.geeruh.support.assertions;


import org.assertj.core.api.AbstractAssert;
import org.assertj.core.api.Assertions;
import pl.edu.pw.elka.paprykaisalami.geeruh.statuses.domain.models.Status;
import pl.edu.pw.elka.paprykaisalami.geeruh.statuses.domain.models.StatusCode;

public class StatusAssert extends AbstractAssert<StatusAssert, Status> {

    public StatusAssert(Status actual) {
        super(actual, StatusAssert.class);
    }

    public StatusAssert hasCode(String statusCode) {
        isNotNull();

        Assertions.assertThat(this.actual)
                .hasFieldOrPropertyWithValue("statusCode", new StatusCode(statusCode));

        return myself;
    }

    public StatusAssert hasName(String name) {
        isNotNull();

        Assertions.assertThat(this.actual)
                .hasFieldOrPropertyWithValue("name", name);

        return myself;
    }

    public StatusAssert hasOrderNumber(Integer orderNumber) {
        isNotNull();

        Assertions.assertThat(this.actual)
                .hasFieldOrPropertyWithValue("orderNumber", orderNumber);

        return myself;
    }

    public StatusAssert isEquivalentTo(Status expected) {
        isNotNull();

        Assertions.assertThat(actual)
                .usingRecursiveComparison()
                .isEqualTo(expected);

        return myself;
    }
}
