package pl.edu.pw.elka.paprykaisalami.geeruh.comments.adapters.api;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
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
import java.security.Principal;
import java.util.List;
import java.util.UUID;

import static pl.edu.pw.elka.paprykaisalami.geeruh.issues.domain.models.IssueId.ISSUE_ID_REGEX;

@Validated
@AllArgsConstructor
@RestController
@RequestMapping("comments")
@Tag(name = "Comments")
class CommentEndpoint {
    private final CommentFacade commentFacade;

    @GetMapping
    public List<CommentResponse> list(
            @Pattern(regexp = ISSUE_ID_REGEX) @Size(min = 2, max = 5) final String issueId
    ) {
        return commentFacade.list(issueId);
    }

    @GetMapping("{commentId}")
    public CommentResponse get(@PathVariable final UUID commentId) {
        return commentFacade.get(commentId);
    }

    @PutMapping("{commentId}")
    public CommentResponse update(
            @PathVariable final UUID commentId,
            @Valid @RequestBody final CommentRequest commentRequest
    ) {
        return commentFacade.update(commentId, commentRequest);
    }

    @PostMapping
    public CommentResponse create(
            @NotNull @Pattern(regexp = ISSUE_ID_REGEX) @Size(min = 2, max = 5) final String issueId,
            Principal principal,
            @Valid @RequestBody final CommentRequest commentRequest
    ) {
        return commentFacade.create(issueId, principal.getName(), commentRequest);
    }

    @DeleteMapping("{commentId}")
    public void delete(@PathVariable final UUID commentId) {
        commentFacade.delete(commentId);
    }
}
