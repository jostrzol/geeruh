package pl.edu.pw.elka.paprykaisalami.geeruh.comments.domain.ports;


import io.vavr.control.Either;
import pl.edu.pw.elka.paprykaisalami.geeruh.comments.domain.models.CommentId;
import pl.edu.pw.elka.paprykaisalami.geeruh.comments.domain.models.Comment;
import pl.edu.pw.elka.paprykaisalami.geeruh.issues.domain.models.IssueId;
import pl.edu.pw.elka.paprykaisalami.geeruh.users.domain.models.UserId;
import pl.edu.pw.elka.paprykaisalami.geeruh.utils.DomainError;

import java.time.Instant;
import java.util.List;

public interface CommentRepository {

    List<Comment> findAll();

    List<Comment> findAllForIssue(IssueId issueId);

    Either<DomainError, Comment> findById(CommentId commentId);

    Comment create(IssueId issueId, UserId creatorUserId, String content, Instant createdAt);

    Comment save(Comment comment);

    void delete(Comment comment);
}
