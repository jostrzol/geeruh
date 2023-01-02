package pl.edu.pw.elka.paprykaisalami.geeruh.comments.adapters.persistent;

import io.vavr.control.Either;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.pw.elka.paprykaisalami.geeruh.comments.domain.models.Comment;
import pl.edu.pw.elka.paprykaisalami.geeruh.comments.domain.models.CommentId;
import pl.edu.pw.elka.paprykaisalami.geeruh.comments.domain.ports.CommentRepository;
import pl.edu.pw.elka.paprykaisalami.geeruh.issues.adapters.persistent.ActualPersistentIssueRepository;
import pl.edu.pw.elka.paprykaisalami.geeruh.issues.adapters.persistent.IssuePersistent;
import pl.edu.pw.elka.paprykaisalami.geeruh.issues.domain.models.Issue;
import pl.edu.pw.elka.paprykaisalami.geeruh.issues.domain.models.IssueId;
import pl.edu.pw.elka.paprykaisalami.geeruh.users.adapters.persistent.ActualPersistentUserRepository;
import pl.edu.pw.elka.paprykaisalami.geeruh.users.domain.models.UserId;
import pl.edu.pw.elka.paprykaisalami.geeruh.utils.DomainError;
import pl.edu.pw.elka.paprykaisalami.geeruh.utils.DomainError.NotFoundDomainError;

import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static pl.edu.pw.elka.paprykaisalami.geeruh.issues.adapters.persistent.IssuePersistent.IssuePersistentId;

@AllArgsConstructor
@Primary
@Component
class PersistentCommentRepository implements CommentRepository {

    ActualPersistentCommentRepository actualRepository;

    ActualPersistentUserRepository actualPersistentUserRepository;

    ActualPersistentIssueRepository actualPersistentIssueRepository;

    @Override
    public List<Comment> findAll() {
        return actualRepository.findAll()
                .stream()
                .map(CommentPersistent::toComment)
                .collect(Collectors.toList());
    }

    @Override
    public List<Comment> findAllForIssue(IssueId issueId) {
        var issue = actualPersistentIssueRepository.getReferenceById(IssuePersistentId.of(issueId));
        return actualRepository.findAllByIssue(issue)
                .stream()
                .map(CommentPersistent::toComment)
                .collect(Collectors.toList());
    }

    @Override
    public Either<DomainError, Comment> findById(CommentId commentId) {
        return actualRepository.findById(commentId.value())
                .<Either<DomainError, CommentPersistent>>map(Either::right)
                .orElseGet(NotFoundDomainError.supplier(Comment.class, commentId.value()))
                .map(CommentPersistent::toComment);
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public Comment create(
            IssueId issueId,
            UserId creatorUserId,
            String content,
            Instant createdAt
    ) {
        var issue = actualPersistentIssueRepository.getReferenceById(IssuePersistentId.of(issueId));
        var creator = actualPersistentUserRepository.getReferenceById(creatorUserId.value());
        var commentPersistent = new CommentPersistent(
                issue,
                creator,
                content,
                createdAt);
        return actualRepository.save(commentPersistent)
                .toComment();
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public Comment save(Comment comment) {
        var issue = actualPersistentIssueRepository.getReferenceById(IssuePersistentId.of(comment.getIssueId()));
        var creator = actualPersistentUserRepository.getReferenceById(comment.getCreatorUserId().value());
        var commentPersistent = CommentPersistent.of(comment, issue, creator);
        return actualRepository.save(commentPersistent).toComment();
    }

    @Override
    public void delete(Comment comment) {
        actualRepository.deleteById(comment.getCommentId().value());
    }
}

@Component
interface ActualPersistentCommentRepository extends
        JpaRepository<CommentPersistent, UUID> {
     List<CommentPersistent> findAllByIssue(IssuePersistent issue);
}