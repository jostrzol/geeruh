package pl.edu.pw.elka.paprykaisalami.geeruh.statuses.domain.ports;


import io.vavr.control.Either;
import pl.edu.pw.elka.paprykaisalami.geeruh.statuses.domain.models.Status;
import pl.edu.pw.elka.paprykaisalami.geeruh.statuses.domain.models.StatusCode;
import pl.edu.pw.elka.paprykaisalami.geeruh.utils.DomainError;

import java.util.List;

public interface StatusRepository {

    List<Status> findAll();

    Either<DomainError, Status> findByCode(StatusCode statusCode);

    Status save(Status status);
}
