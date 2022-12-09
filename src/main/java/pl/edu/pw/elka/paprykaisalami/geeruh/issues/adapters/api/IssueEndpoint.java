package pl.edu.pw.elka.paprykaisalami.geeruh.issues.adapters.api;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

@Validated
@AllArgsConstructor
@RestController
@RequestMapping("issues")
@Tag(name = "Issues")
class IssueEndpoint {

    private static final String ISSUE_ID = "^[A-Z]{2,5}-[0-9]+$";

    private final IssueFacade issueFacade;

    @GetMapping
    public List<IssueResponse> list() {
        return issueFacade.list();
    }

    @GetMapping("{issueId}")
    public IssueResponse get(@PathVariable @Pattern(regexp = ISSUE_ID) final String issueId) {
        return issueFacade.get(issueId);
    }

    @GetMapping("{issueId}/history")
    public List<IssueHistoryResponse> getHistory(@PathVariable @Pattern(regexp = ISSUE_ID) final String issueId) {
        return issueFacade.getHistory(issueId);
    }

    @PutMapping("{issueId}")
    public IssueResponse update(
            @PathVariable @Pattern(regexp = ISSUE_ID) final String issueId,
            @Valid @RequestBody final IssueRequest issueRequest
    ) {
        return issueFacade.update(issueId, issueRequest);
    }

    @PostMapping
    public IssueResponse create(
            @NotNull @Pattern(regexp = "^[A-Z]+$") @Size(min = 2, max = 5) final String projectCode,
            @Valid @RequestBody final IssueRequest issueRequest
    ) {
        return issueFacade.create(projectCode, issueRequest);
    }
}