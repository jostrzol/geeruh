package pl.edu.pw.elka.paprykaisalami.geeruh.issues.domain.models;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.Value;
import pl.edu.pw.elka.paprykaisalami.geeruh.projects.domain.models.ProjectCode;

public record IssueId(@NonNull ProjectCode projectCode, @NonNull Integer issueIndex) {

    @Override
    public String toString() {
        return projectCode.value() + "-" + issueIndex;
    }
}
