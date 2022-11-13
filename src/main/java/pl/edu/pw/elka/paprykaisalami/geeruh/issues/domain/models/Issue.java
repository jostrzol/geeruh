package pl.edu.pw.elka.paprykaisalami.geeruh.issues.domain.models;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import org.jetbrains.annotations.Nullable;

@Builder
@Data
public class Issue {

    @NonNull
    IssueId issueId;

    @NonNull
    IssueType type;

    @NonNull
    Summary summary;

    @Nullable
    Description description;
}
