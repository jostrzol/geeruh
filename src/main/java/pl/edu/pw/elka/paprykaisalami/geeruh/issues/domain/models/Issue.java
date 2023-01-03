package pl.edu.pw.elka.paprykaisalami.geeruh.issues.domain.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import pl.edu.pw.elka.paprykaisalami.geeruh.statuses.domain.models.StatusCode;
import pl.edu.pw.elka.paprykaisalami.geeruh.users.domain.models.UserId;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Builder
@Getter
public class Issue {

    @NotNull
    @Valid
    IssueId issueId;

    @NotNull
    @Setter
    StatusCode statusCode;

    @NotNull
    @Setter
    IssueType type;

    @NotNull
    @Valid
    @Setter
    Summary summary;

    @NotNull
    @Valid
    @Setter
    Description description;

    @Valid
    @Setter
    UserId assigneeUserId;

    @Valid
    @Setter
    @Builder.Default
    Set<IssueId> relatedIssues = new HashSet<>();

    @Valid
    @Setter
    @Builder.Default
    Set<IssueId> relatedIssuesChildren = new HashSet<>();

    public void relateTo(IssueId relatedIssueId) {
        this.getRelatedIssues().add(relatedIssueId);
    }

    public void breakRelationWith(IssueId relatedIssueId) {
        this.getRelatedIssues().remove(relatedIssueId);
    }

}
