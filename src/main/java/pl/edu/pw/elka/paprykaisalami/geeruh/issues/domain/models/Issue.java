package pl.edu.pw.elka.paprykaisalami.geeruh.issues.domain.models;

import lombok.*;
import org.jetbrains.annotations.Nullable;

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
