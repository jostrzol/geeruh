package pl.edu.pw.elka.paprykaisalami.geeruh.issues.domain.models;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Builder
@Getter
public class Issue {

    @NonNull
    IssueId issueId;

    @NonNull
    @Setter
    IssueType type;

    @NonNull
    @Setter
    Summary summary;

    @NonNull
    @Setter
    Description description;
}
