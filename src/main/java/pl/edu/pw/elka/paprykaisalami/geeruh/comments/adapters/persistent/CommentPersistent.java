package pl.edu.pw.elka.paprykaisalami.geeruh.comments.adapters.persistent;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import pl.edu.pw.elka.paprykaisalami.geeruh.comments.domain.models.Comment;
import pl.edu.pw.elka.paprykaisalami.geeruh.comments.domain.models.CommentId;
import pl.edu.pw.elka.paprykaisalami.geeruh.issues.adapters.persistent.IssuePersistent;
import pl.edu.pw.elka.paprykaisalami.geeruh.users.adapters.persistent.UserPersistent;
import pl.edu.pw.elka.paprykaisalami.geeruh.users.domain.models.UserId;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.Instant;
import java.util.UUID;

@NoArgsConstructor
@Data
@Entity
@Table(name = "Comments")
public class CommentPersistent {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "comment_id", updatable = false, nullable = false)
    @Type(type = "org.hibernate.type.UUIDCharType")
    private UUID commentId;

    @ManyToOne
    private IssuePersistent issue;

    @ManyToOne
    private UserPersistent creator;

    @Lob
    private String content;

    @Column(name = "created_at")
    private Instant createdAt;

    @Column(name = "modified_at")
    private Instant modifiedAt;

    CommentPersistent(
            IssuePersistent issue,
            UserPersistent creator,
            String content,
            Instant createdAt
    ) {
        this.issue = issue;
        this.creator = creator;
        this.content = content;
        this.createdAt = createdAt;
        this.modifiedAt = null;
    }

    CommentPersistent(
            UUID commentId,
            IssuePersistent issue,
            UserPersistent creator,
            String content,
            Instant createdAt,
            Instant modifiedAt
    ) {
        this.commentId = commentId;
        this.issue = issue;
        this.creator = creator;
        this.content = content;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

    public Comment toComment() {
        return Comment.builder()
                .commentId(new CommentId(commentId))
                .creatorUserId(new UserId(creator.getUserId()))
                .issueId(issue.getIssueId())
                .content(content)
                .createdAt(createdAt)
                .modifiedAt(modifiedAt)
                .build();
    }

    public static CommentPersistent of(Comment comment, IssuePersistent issue, UserPersistent creator) {
        return new CommentPersistent(
                comment.getCommentId().value(),
                issue,
                creator,
                comment.getContent(),
                comment.getCreatedAt(),
                comment.getModifiedAt());
    }
}
