package pl.edu.pw.elka.paprykaisalami.geeruh.issues.domain.models;

import lombok.NonNull;
import lombok.Value;
import pl.edu.pw.elka.paprykaisalami.geeruh.projects.domain.models.ProjectCode;

@Value(staticConstructor = "of")
public class IssueId {

    @NonNull
    ProjectCode projectCode;

    @NonNull
    Integer issueIndex;

    @Override
    public String toString() {
        return projectCode.getValue() + "-" + issueIndex;
    }
}
