package pl.edu.pw.elka.paprykaisalami.geeruh.issues.domain.ports;

import lombok.AllArgsConstructor;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Component;
import pl.edu.pw.elka.paprykaisalami.geeruh.issues.domain.models.Issue;
import pl.edu.pw.elka.paprykaisalami.geeruh.issues.domain.models.Description;
import pl.edu.pw.elka.paprykaisalami.geeruh.issues.domain.models.IssueId;
import pl.edu.pw.elka.paprykaisalami.geeruh.issues.domain.models.IssueType;
import pl.edu.pw.elka.paprykaisalami.geeruh.issues.domain.models.Summary;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Component
public class IssueService {

    private final IssueRepository issueRepository;

    public List<Issue> list() {
        return issueRepository.findAll();
    }

    public Optional<Issue> get(IssueId issueId) {
        return issueRepository.findById(issueId);
    }

    public Issue create(IssueType type, Summary summary, @Nullable Description description) {
        return issueRepository.save(type, summary, description);
    }
}
