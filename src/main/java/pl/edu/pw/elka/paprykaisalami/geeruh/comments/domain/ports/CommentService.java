package pl.edu.pw.elka.paprykaisalami.geeruh.comments.domain.ports;

import io.vavr.control.Either;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import pl.edu.pw.elka.paprykaisalami.geeruh.comments.domain.models.CommentId;
import pl.edu.pw.elka.paprykaisalami.geeruh.comments.domain.models.Comment;
import pl.edu.pw.elka.paprykaisalami.geeruh.issues.domain.models.IssueId;
import pl.edu.pw.elka.paprykaisalami.geeruh.issues.domain.ports.IssueService;
import pl.edu.pw.elka.paprykaisalami.geeruh.users.domain.ports.UserService;
import pl.edu.pw.elka.paprykaisalami.geeruh.utils.DomainError;

import javax.validation.Valid;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Validated
@AllArgsConstructor
@Component
@Transactional(readOnly = true)
public class CommentService {

    private final IssueService issueService;

    private final UserService userService;

    private final CommentRepository commentRepository;


    public List<Comment> list() {
        return commentRepository.findAll();
    }


    public Either<DomainError, Comment> get(CommentId commentId) {
        return commentRepository.findById(commentId);
    }

    @Valid
    @Transactional
    public Either<DomainError, Comment> create(
            IssueId issueId,
            String login,
            String content
    ) {
        var issue = issueService.get(issueId);
        if (issue.isLeft()) {
            return Either.left(issue.getLeft());
        }
        return userService.getByLogin(login)
                .map(user -> commentRepository.create(issueId, user.getUserId(), content, Instant.now()));
    }

    @Valid
    @Transactional
    public Either<DomainError, Comment> update(
            CommentId commentId,
            String content
    ) {
        return commentRepository.findById(commentId).map(
                comment -> {
                    comment.setContent(content);
                    return commentRepository.save(comment);
                });
    }

    @Valid
    @Transactional
    public Optional<DomainError> delete(CommentId commentId) {
        return commentRepository.findById(commentId)
                .map(comment -> {
                    commentRepository.delete(comment);
                    return null;
                })
                .swap().toJavaOptional();
    }

    public Either<DomainError, List<Comment>> listForIssue(IssueId issueId) {
        return issueService.get(issueId)
                .map(issue -> commentRepository.findAllForIssue(issueId));
    }
}
