package pl.edu.pw.elka.paprykaisalami.geeruh.comments.domain.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import pl.edu.pw.elka.paprykaisalami.geeruh.issues.domain.models.IssueId;
import pl.edu.pw.elka.paprykaisalami.geeruh.users.domain.models.UserId;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.Instant;

@Builder
@Getter
public class Comment {

    @NotNull
    @Valid
    CommentId commentId;

    @NotNull
    @Valid
    IssueId issueId;

    @NotNull
    @Valid
    UserId creatorUserId;

    @NotBlank
    String content;

    @NotNull
    Instant createdAt;

    Instant modifiedAt;

    public void setContent(String content) {
        this.content = content;
        setModifiedAtToNow();
    }

    private void setModifiedAtToNow() {
        modifiedAt = Instant.now();
    }
}
