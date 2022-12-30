package pl.edu.pw.elka.paprykaisalami.geeruh.issues.domain.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import pl.edu.pw.elka.paprykaisalami.geeruh.statuses.domain.models.StatusCode;
import pl.edu.pw.elka.paprykaisalami.geeruh.users.domain.models.UserId;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

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
}
