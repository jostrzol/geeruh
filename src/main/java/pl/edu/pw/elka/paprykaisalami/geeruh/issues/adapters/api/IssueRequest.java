package pl.edu.pw.elka.paprykaisalami.geeruh.issues.adapters.api;

import lombok.AllArgsConstructor;
import lombok.Value;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pl.edu.pw.elka.paprykaisalami.geeruh.issues.domain.models.IssueType;

@AllArgsConstructor
@Value
class IssueRequest {

    @NotNull
    IssueType type;

    @NotNull
    String summary;

    @Nullable
    String description;
}
