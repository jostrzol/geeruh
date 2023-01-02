package pl.edu.pw.elka.paprykaisalami.geeruh.issues.domain.models;

import pl.edu.pw.elka.paprykaisalami.geeruh.projects.domain.models.ProjectCode;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

public record IssueId(@NotNull ProjectCode projectCode, @NotNull @PositiveOrZero Integer issueIndex) {

    public static final String ISSUE_ID_REGEX = "^[A-Z]{2,5}-[0-9]+$";

    public static IssueId of(String text) {
        var parts = text.split("-", 2);

        var projectCode = parts[0];
        var issueNumber = Integer.parseUnsignedInt(parts[1]);

        return new IssueId(new ProjectCode(projectCode), issueNumber);
    }

    @Override
    public String toString() {
        return projectCode.value() + "-" + issueIndex;
    }
}
