package pl.edu.pw.elka.paprykaisalami.geeruh.comments.adapters.api;

import lombok.AllArgsConstructor;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Component;
import pl.edu.pw.elka.paprykaisalami.geeruh.comments.domain.models.Comment;
import pl.edu.pw.elka.paprykaisalami.geeruh.comments.domain.models.CommentId;
import pl.edu.pw.elka.paprykaisalami.geeruh.comments.domain.ports.CommentService;
import pl.edu.pw.elka.paprykaisalami.geeruh.issues.domain.models.Issue;
import pl.edu.pw.elka.paprykaisalami.geeruh.issues.domain.models.IssueId;
import pl.edu.pw.elka.paprykaisalami.geeruh.users.domain.models.UserId;
import pl.edu.pw.elka.paprykaisalami.geeruh.utils.DomainError;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@AllArgsConstructor
@Component
class CommentFacade {

    private final CommentService commentService;

    public List<CommentResponse> list(@Nullable String issueId) {
        List<Comment> comments;
        if (issueId != null) {
            comments = commentService.listForIssue(IssueId.of(issueId))
                    .getOrElseThrow(DomainError::toException);
        }
        else {
            comments = commentService.list();
        }
        return comments.stream()
                .map(CommentResponse::of)
                .collect(Collectors.toList());
    }

    public CommentResponse get(UUID commentId) {
        var issue = commentService.get(new CommentId(commentId))
                .getOrElseThrow(DomainError::toException);
        return CommentResponse.of(issue);
    }

    public CommentResponse create(String rawIssueId, String login, CommentRequest commentRequest) {
        var issue = commentService.create(
                IssueId.of(rawIssueId),
                login,
                commentRequest.content
        ).getOrElseThrow(DomainError::toException);
        return CommentResponse.of(issue);
    }


    public CommentResponse update(UUID commentId, CommentRequest commentRequest) {
        var issue = commentService.update(
                new CommentId(commentId),
                commentRequest.content
        ).getOrElseThrow(DomainError::toException);
        return CommentResponse.of(issue);
    }

    public void delete(UUID commentId) {
        commentService.delete(new CommentId(commentId))
                .map(error -> {
                    throw error.toException();
                });
    }
}
