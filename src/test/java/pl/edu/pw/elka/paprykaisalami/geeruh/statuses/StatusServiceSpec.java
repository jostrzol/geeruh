package pl.edu.pw.elka.paprykaisalami.geeruh.statuses;

import org.junit.jupiter.api.Test;
import pl.edu.pw.elka.paprykaisalami.geeruh.BaseSpec;
import pl.edu.pw.elka.paprykaisalami.geeruh.statuses.domain.models.StatusCode;
import pl.edu.pw.elka.paprykaisalami.geeruh.utils.DomainError.NotFoundDomainError;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.vavr.api.VavrAssertions.assertThat;
import static pl.edu.pw.elka.paprykaisalami.geeruh.support.StatusAttributeDataset.FIRST_STATUS_CODE;
import static pl.edu.pw.elka.paprykaisalami.geeruh.support.StatusAttributeDataset.FIRST_STATUS_ORDER_NUMBER;
import static pl.edu.pw.elka.paprykaisalami.geeruh.support.StatusAttributeDataset.FIRST_STATUS_NAME;
import static pl.edu.pw.elka.paprykaisalami.geeruh.support.StatusAttributeDataset.SECOND_STATUS_CODE;
import static pl.edu.pw.elka.paprykaisalami.geeruh.support.StatusAttributeDataset.SECOND_STATUS_NAME;
import static pl.edu.pw.elka.paprykaisalami.geeruh.support.assertions.GeeruhAssertions.assertThat;
import static pl.edu.pw.elka.paprykaisalami.geeruh.support.datasets.StatusDataset.firstStatus;
import static pl.edu.pw.elka.paprykaisalami.geeruh.support.datasets.StatusDataset.secondStatus;

public class StatusServiceSpec extends BaseSpec {

    @Test
    void shouldCreateStatus() {
        // given
        var status = statusService.create(
                new StatusCode(FIRST_STATUS_CODE),
                FIRST_STATUS_NAME,
                FIRST_STATUS_ORDER_NUMBER
        );

        // expect
        assertThat(status).isEquivalentTo(firstStatus());
    }

    @Test
    void shouldGetStatus() {
        // given
        thereAreStatuses(firstStatus());

        // when
        var status = statusService.get(new StatusCode(FIRST_STATUS_CODE));

        // expect
        assertThat(status).hasRightValueSatisfying(p -> assertThat(p)
                .isEquivalentTo(firstStatus())
        );
    }

    @Test
    void shouldNotGetStatus_whenAbsent() {
        // given
        thereAreStatuses(firstStatus());

        // when
        var status = statusService.get(new StatusCode(SECOND_STATUS_CODE));

        // expect
        assertThat(status).containsLeftInstanceOf(NotFoundDomainError.class);
    }

    @Test
    void shouldListAllStatuses() {
        // given
        thereAreStatuses(firstStatus(), secondStatus());

        // when
        var statuses = statusService.list();

        // expect
        assertThat(statuses)
                .usingRecursiveFieldByFieldElementComparator()
                .containsExactlyInAnyOrder(firstStatus(), secondStatus());
    }

    @Test
    void shouldListNoStatuses_whenNoneCreated() {
        // given
        var statuses = statusService.list();

        // expect
        assertThat(statuses).isEmpty();
    }

    @Test
    void shouldUpdateStatus() {
        // given
        thereAreStatuses(firstStatus());

        // when
        var status = statusService.update(
                new StatusCode(FIRST_STATUS_CODE),
                SECOND_STATUS_NAME,
                FIRST_STATUS_ORDER_NUMBER
        );

        // expect
        assertThat(status).hasRightValueSatisfying(p -> assertThat(p)
                .hasCode(FIRST_STATUS_CODE)
                .hasName(SECOND_STATUS_NAME)
                .hasOrderNumber(FIRST_STATUS_ORDER_NUMBER)
        );
    }

    @Test
    void shouldNotUpdateStatus_whenAbsent() {
        // given
        var status = statusService.update(
                new StatusCode(FIRST_STATUS_CODE),
                SECOND_STATUS_NAME,
                FIRST_STATUS_ORDER_NUMBER
        );

        // expect
        assertThat(status).containsLeftInstanceOf(NotFoundDomainError.class);
    }
}
