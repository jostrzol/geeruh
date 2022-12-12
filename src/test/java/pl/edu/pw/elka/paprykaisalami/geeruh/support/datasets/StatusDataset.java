package pl.edu.pw.elka.paprykaisalami.geeruh.support.datasets;

import pl.edu.pw.elka.paprykaisalami.geeruh.statuses.domain.models.Status;
import pl.edu.pw.elka.paprykaisalami.geeruh.statuses.domain.models.StatusCode;

import static pl.edu.pw.elka.paprykaisalami.geeruh.support.StatusAttributeDataset.FIRST_STATUS_CODE;
import static pl.edu.pw.elka.paprykaisalami.geeruh.support.StatusAttributeDataset.FIRST_STATUS_ORDER_NUMBER;
import static pl.edu.pw.elka.paprykaisalami.geeruh.support.StatusAttributeDataset.FIRST_STATUS_NAME;
import static pl.edu.pw.elka.paprykaisalami.geeruh.support.StatusAttributeDataset.SECOND_STATUS_CODE;
import static pl.edu.pw.elka.paprykaisalami.geeruh.support.StatusAttributeDataset.SECOND_STATUS_ORDER_NUMBER;
import static pl.edu.pw.elka.paprykaisalami.geeruh.support.StatusAttributeDataset.SECOND_STATUS_NAME;

public class StatusDataset {

    public static Status firstStatus() {
        return Status.builder()
                .statusCode(new StatusCode(FIRST_STATUS_CODE))
                .name(FIRST_STATUS_NAME)
                .orderNumber(FIRST_STATUS_ORDER_NUMBER)
                .build();
    }

    public static Status secondStatus() {
        return Status.builder()
                .statusCode(new StatusCode(SECOND_STATUS_CODE))
                .name(SECOND_STATUS_NAME)
                .orderNumber(SECOND_STATUS_ORDER_NUMBER)
                .build();
    }
}
