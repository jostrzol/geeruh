package pl.edu.pw.elka.paprykaisalami.geeruh.comments.adapters.api;

import pl.edu.pw.elka.paprykaisalami.geeruh.comments.domain.models.Comment;

import java.time.Instant;
import java.util.UUID;

public record CommentResponse(
        UUID commentId,
        String issueId,
        UUID creatorUserId,
        String content,
        Instant createdAt,
        Instant modifiedAt
) {

    public static CommentResponse of(Comment comment) {
        return new CommentResponse(
                comment.getCommentId().value(),
                comment.getIssueId().toString(),
                comment.getCreatorUserId().value(),
                comment.getContent(),
                comment.getCreatedAt(),
                comment.getModifiedAt());
    }
}
