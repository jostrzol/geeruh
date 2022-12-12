package pl.edu.pw.elka.paprykaisalami.geeruh.statuses.domain.ports;

import io.vavr.control.Either;
import lombok.AllArgsConstructor;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import pl.edu.pw.elka.paprykaisalami.geeruh.statuses.domain.models.Status;
import pl.edu.pw.elka.paprykaisalami.geeruh.statuses.domain.models.StatusCode;
import pl.edu.pw.elka.paprykaisalami.geeruh.utils.DomainError;

import javax.validation.Valid;

import java.util.List;

@AllArgsConstructor
@Validated
@Component
@Transactional(readOnly = true)
public class StatusService {

    StatusRepository statusRepository;

    public List<Status> list() {
        return statusRepository.findAll();
    }

    public Either<DomainError, Status> get(StatusCode statusCode) {
        return statusRepository.findByCode(statusCode);
    }

    @Transactional
    @Valid
    public Status create(StatusCode statusCode, String name, Integer orderNumber) {
        var status = Status.builder()
                .statusCode(statusCode)
                .name(name)
                .orderNumber(orderNumber)
                .build();
        return statusRepository.save(status);
    }

    @Transactional
    @Valid
    public Either<DomainError, Status> update(StatusCode statusCode, String name, Integer orderNumber) {
        return statusRepository.findByCode(statusCode).map(
                status -> {
                    status.setName(name);
                    status.setOrderNumber(orderNumber);
                    return statusRepository.save(status);
                });
    }

}
