package pl.edu.pw.elka.paprykaisalami.geeruh.statuses.adapters.api;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.edu.pw.elka.paprykaisalami.geeruh.statuses.domain.models.StatusCode;
import pl.edu.pw.elka.paprykaisalami.geeruh.statuses.domain.ports.StatusService;
import pl.edu.pw.elka.paprykaisalami.geeruh.utils.DomainError;

import java.util.List;

@AllArgsConstructor
@Component
public class StatusFacade {

    StatusService statusService;

    public List<StatusResponse> list() {
        return statusService.list().stream()
                .map(StatusResponse::of)
                .toList();
    }

    public StatusResponse get(String statusCode) {
        var status = statusService.get(new StatusCode(statusCode))
                .getOrElseThrow(DomainError::toException);
        return StatusResponse.of(status);
    }

    public StatusResponse update(String statusCode, StatusRequest statusRequest) {
        var status = statusService.update(
                new StatusCode(statusCode),
                statusRequest.getName(),
                statusRequest.getOrderNumber()
        ).getOrElseThrow(DomainError::toException);
        return StatusResponse.of(status);
    }

    public StatusResponse create(String statusCode, StatusRequest statusRequest) {
        var status = statusService.create(
                new StatusCode(statusCode),
                statusRequest.getName(),
                statusRequest.getOrderNumber()
        );
        return StatusResponse.of(status);
    }
}
