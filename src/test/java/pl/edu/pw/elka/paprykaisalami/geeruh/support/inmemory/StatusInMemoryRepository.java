package pl.edu.pw.elka.paprykaisalami.geeruh.support.inmemory;

import io.vavr.control.Either;
import pl.edu.pw.elka.paprykaisalami.geeruh.statuses.domain.models.Status;
import pl.edu.pw.elka.paprykaisalami.geeruh.statuses.domain.models.StatusCode;
import pl.edu.pw.elka.paprykaisalami.geeruh.statuses.domain.ports.StatusRepository;
import pl.edu.pw.elka.paprykaisalami.geeruh.utils.DomainError;

public class StatusInMemoryRepository extends BaseInMemoryRepository<Status, StatusCode> implements StatusRepository {

    public StatusInMemoryRepository() {
        super(Status.class, Status::getStatusCode);
    }

    @Override
    public Either<DomainError, Status> findByCode(StatusCode statusCode) {
        return findById(statusCode);
    }
}
