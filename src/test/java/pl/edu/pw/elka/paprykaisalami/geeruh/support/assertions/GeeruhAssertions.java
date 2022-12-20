package pl.edu.pw.elka.paprykaisalami.geeruh.support.assertions;

import pl.edu.pw.elka.paprykaisalami.geeruh.issues.domain.models.Issue;
import pl.edu.pw.elka.paprykaisalami.geeruh.projects.domain.models.Project;
import pl.edu.pw.elka.paprykaisalami.geeruh.statuses.domain.models.Status;

public class GeeruhAssertions {

    public static ProjectAssert assertThat(Project actual) {
        return new ProjectAssert(actual);
    }

    public static StatusAssert assertThat(Status actual) {
        return new StatusAssert(actual);
    }

    public static IssueAssert assertThat(Issue actual) {
        return new IssueAssert(actual);
    }
}
