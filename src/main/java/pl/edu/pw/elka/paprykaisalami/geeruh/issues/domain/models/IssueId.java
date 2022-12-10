package pl.edu.pw.elka.paprykaisalami.geeruh.issues.domain.models;

import pl.edu.pw.elka.paprykaisalami.geeruh.projects.domain.models.ProjectCode;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

public record IssueId(@NotNull ProjectCode projectCode, @NotNull @PositiveOrZero Integer issueIndex) {

    @Override
    public String toString() {
        return projectCode.value() + "-" + issueIndex;
    }
}
