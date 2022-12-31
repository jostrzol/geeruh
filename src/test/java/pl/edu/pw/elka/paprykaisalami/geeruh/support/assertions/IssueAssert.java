package pl.edu.pw.elka.paprykaisalami.geeruh.support.assertions;


import org.assertj.core.api.AbstractAssert;
import org.assertj.core.api.Assertions;
import pl.edu.pw.elka.paprykaisalami.geeruh.issues.domain.models.Description;
import pl.edu.pw.elka.paprykaisalami.geeruh.issues.domain.models.Issue;
import pl.edu.pw.elka.paprykaisalami.geeruh.issues.domain.models.IssueId;
import pl.edu.pw.elka.paprykaisalami.geeruh.issues.domain.models.IssueType;
import pl.edu.pw.elka.paprykaisalami.geeruh.issues.domain.models.Summary;
import pl.edu.pw.elka.paprykaisalami.geeruh.projects.domain.models.ProjectCode;
import pl.edu.pw.elka.paprykaisalami.geeruh.statuses.domain.models.StatusCode;

import static org.assertj.core.api.Assertions.assertThat;

public class IssueAssert extends AbstractAssert<IssueAssert, Issue> {

    public IssueAssert(Issue actual) {
        super(actual, IssueAssert.class);
    }

    public IssueAssert hasProjectWithCode(String projectCode) {
        isNotNull();

        assertThat(actual)
                .hasFieldOrProperty("projectCode");

        assertThat(actual.getIssueId())
                .hasFieldOrPropertyWithValue("projectCode", new ProjectCode(projectCode));

        return myself;
    }

    public IssueAssert hasIssueIndex(Integer index) {
        isNotNull();

        assertThat(actual)
                .hasFieldOrProperty("projectCode");

        assertThat(actual.getIssueId())
                .hasFieldOrPropertyWithValue("issueIndex", index);

        return myself;
    }

    public IssueAssert hasIssueId(IssueId issueId) {
        isNotNull();

        assertThat(actual)
                .hasFieldOrPropertyWithValue("issueId", issueId);

        return myself;
    }

    public IssueAssert hasStatusCode(String statusCode) {
        isNotNull();

        assertThat(actual)
                .hasFieldOrPropertyWithValue("statusCode", new StatusCode(statusCode));

        return myself;
    }

    public IssueAssert hasRelatedIssue(IssueId relatedIssueId) {
        isNotNull();

        assertThat(actual)
                .hasFieldOrPropertyWithValue("relatedIssueId", relatedIssueId);

        return myself;
    }

    public IssueAssert hasType(String type) {
        isNotNull();

        assertThat(actual)
                .hasFieldOrPropertyWithValue("type", IssueType.valueOf(type));

        return myself;
    }

    public IssueAssert hasSummary(String summary) {
        isNotNull();

        assertThat(actual)
                .hasFieldOrPropertyWithValue("summary", new Summary(summary));

        return myself;
    }

    public IssueAssert hasDescription(String description) {
        isNotNull();

        assertThat(actual)
                .hasFieldOrPropertyWithValue("description", new Description(description));

        return myself;
    }

    public IssueAssert isEquivalentTo(Issue expected) {
        isNotNull();

        assertThat(actual)
                .usingRecursiveComparison()
                .isEqualTo(expected);

        return myself;
    }

    public IssueAssert isEquivalentOmittingIndexTo(Issue expected) {
        isNotNull();

        assertThat(actual)
                .usingRecursiveComparison()
                .ignoringFields("issueId.issueIndex")
                .isEqualTo(expected);

        return myself;
    }
}
