package pl.edu.pw.elka.paprykaisalami.geeruh.statuses.adapters.persistent;

import io.vavr.control.Either;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import pl.edu.pw.elka.paprykaisalami.geeruh.statuses.adapters.persistent.StatusPersistent;
import pl.edu.pw.elka.paprykaisalami.geeruh.statuses.domain.models.Status;
import pl.edu.pw.elka.paprykaisalami.geeruh.statuses.domain.models.StatusCode;
import pl.edu.pw.elka.paprykaisalami.geeruh.statuses.domain.ports.StatusRepository;
import pl.edu.pw.elka.paprykaisalami.geeruh.utils.DomainError;
import pl.edu.pw.elka.paprykaisalami.geeruh.utils.DomainError.NotFoundDomainError;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Primary
@Component
@Transactional(readOnly = true)
class PersistentStatusRepository implements StatusRepository {

    ActualPersistentStatusRepository actualRepository;

    @Override
    public List<Status> findAll() {
        return actualRepository.findAll()
                .stream()
                .map(StatusPersistent::toStatus)
                .collect(Collectors.toList());
    }

    @Override
    public Either<DomainError, Status> findByCode(StatusCode statusCode) {
        return actualRepository.findById(statusCode.value())
                .<Either<DomainError, StatusPersistent>>map(Either::right)
                .orElseGet(NotFoundDomainError.supplier(Status.class, statusCode))
                .map(StatusPersistent::toStatus);
    }

    @Override
    @Transactional
    public Status save(Status issue) {
        var statusPersistent = StatusPersistent.of(issue);
        return actualRepository.save(statusPersistent).toStatus();
    }
}
