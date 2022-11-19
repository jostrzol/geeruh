package pl.edu.pw.elka.paprykaisalami.geeruh.issues.adapters.persistent;

import lombok.AllArgsConstructor;
import org.jetbrains.annotations.Nullable;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.pw.elka.paprykaisalami.geeruh.issues.domain.models.*;
import pl.edu.pw.elka.paprykaisalami.geeruh.issues.domain.ports.IssueRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@AllArgsConstructor
@Primary
@Component
public class PersistentIssueRepository implements IssueRepository {

    ActualPersistentIssueRepository actualRepository;

    @Override
    public List<Issue> findAll() {
        return actualRepository.findAll()
                .stream()
                .map(IssuePersistent::toIssue)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Issue> findById(IssueId issueId) {
        return actualRepository.findById(issueId.getValue())
                .map(IssuePersistent::toIssue);
    }

    @Transactional
    @Override
    public Issue save(IssueType type, Summary summary, @Nullable Description description) {
        var issuePersistent = new IssuePersistent(type, summary, description);
        return actualRepository.save(issuePersistent)
                .toIssue();
    }
}

@Component
interface ActualPersistentIssueRepository extends JpaRepository<IssuePersistent, UUID> {
}