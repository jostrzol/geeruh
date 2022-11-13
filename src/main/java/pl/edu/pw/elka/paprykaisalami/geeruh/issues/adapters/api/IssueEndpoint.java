package pl.edu.pw.elka.paprykaisalami.geeruh.issues.adapters.api;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@RestController
@RequestMapping("issues")
class IssueEndpoint {

    private final IssueFacade issueFacade;

    @GetMapping
    public List<IssueResponse> list() {
        return issueFacade.list();
    }

    @GetMapping("{issueId}")
    public IssueResponse get(@PathVariable final UUID issueId) {
        return issueFacade.get(issueId);
    }

    @PostMapping
    public IssueResponse create(@RequestBody final IssueRequest issueRequest) {
        return issueFacade.create(issueRequest);
    }
}