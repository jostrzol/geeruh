package pl.edu.pw.elka.paprykaisalami.geeruh.statuses.adapters.api;

import pl.edu.pw.elka.paprykaisalami.geeruh.statuses.domain.models.Status;

public record StatusResponse(String code, String name, Integer orderNumber) {

    static StatusResponse of(Status status) {
        return new StatusResponse(
                status.getStatusCode().value(),
                status.getName(),
                status.getOrderNumber()
        );
    }
}
