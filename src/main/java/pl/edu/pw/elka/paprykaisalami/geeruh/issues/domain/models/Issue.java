package pl.edu.pw.elka.paprykaisalami.geeruh.issues.domain.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

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
    IssueType type;

    @NotNull
    @Valid
    @Setter
    Summary summary;

    @NotNull
    @Valid
    @Setter
    Description description;
}
