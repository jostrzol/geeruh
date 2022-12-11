package pl.edu.pw.elka.paprykaisalami.geeruh.support.datasets;

import pl.edu.pw.elka.paprykaisalami.geeruh.issues.domain.models.Description;
import pl.edu.pw.elka.paprykaisalami.geeruh.issues.domain.models.Issue;
import pl.edu.pw.elka.paprykaisalami.geeruh.issues.domain.models.IssueId;
import pl.edu.pw.elka.paprykaisalami.geeruh.issues.domain.models.IssueType;
import pl.edu.pw.elka.paprykaisalami.geeruh.issues.domain.models.Summary;
import pl.edu.pw.elka.paprykaisalami.geeruh.projects.domain.models.ProjectCode;

import static pl.edu.pw.elka.paprykaisalami.geeruh.support.IssueAttributeDataset.*;
import static pl.edu.pw.elka.paprykaisalami.geeruh.support.ProjectAttributeDataset.FIRST_PROJECT_CODE;
import static pl.edu.pw.elka.paprykaisalami.geeruh.support.ProjectAttributeDataset.SECOND_PROJECT_CODE;

public class IssueDataset {

    public static IssueId firstIssueId() {
        return new IssueId(
                new ProjectCode(FIRST_PROJECT_CODE),
                1000
        );
    }

    public static Issue firstIssue() {
        return Issue.builder()
                .issueId(firstIssueId())
                .type(IssueType.valueOf(FIRST_ISSUE_TYPE))
                .summary(new Summary(FIRST_ISSUE_SUMMARY))
                .description(new Description(FIRST_ISSUE_DESCRIPTION))
                .build();
    }

    public static IssueId secondIssueId() {
        return new IssueId(
                new ProjectCode(SECOND_PROJECT_CODE),
                1001
        );
    }

    public static Issue secondIssue() {
        return Issue.builder()
                .issueId(secondIssueId())
                .type(IssueType.valueOf(SECOND_ISSUE_TYPE))
                .summary(new Summary(SECOND_ISSUE_SUMMARY))
                .description(new Description(SECOND_ISSUE_DESCRIPTION))
                .build();
    }
}