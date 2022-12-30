package pl.edu.pw.elka.paprykaisalami.geeruh.issues.adapters.persistent;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.hibernate.envers.RelationTargetAuditMode;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.edu.pw.elka.paprykaisalami.geeruh.issues.adapters.persistent.IssuePersistent.IssuePersistentId;
import pl.edu.pw.elka.paprykaisalami.geeruh.issues.domain.models.Description;
import pl.edu.pw.elka.paprykaisalami.geeruh.issues.domain.models.Issue;
import pl.edu.pw.elka.paprykaisalami.geeruh.issues.domain.models.IssueId;
import pl.edu.pw.elka.paprykaisalami.geeruh.issues.domain.models.IssueType;
import pl.edu.pw.elka.paprykaisalami.geeruh.issues.domain.models.Summary;
import pl.edu.pw.elka.paprykaisalami.geeruh.projects.adapters.persistent.ProjectPersistent;
import pl.edu.pw.elka.paprykaisalami.geeruh.projects.domain.models.ProjectCode;
import pl.edu.pw.elka.paprykaisalami.geeruh.statuses.adapters.persistent.StatusPersistent;
import pl.edu.pw.elka.paprykaisalami.geeruh.statuses.domain.models.StatusCode;
import pl.edu.pw.elka.paprykaisalami.geeruh.users.adapters.persistent.UserPersistent;
import pl.edu.pw.elka.paprykaisalami.geeruh.users.domain.models.UserId;

@NoArgsConstructor
@Data
@Entity
@Audited
@Table(name = "Issues")
@IdClass(IssuePersistentId.class)
public class IssuePersistent {

    @Id
    private String projectCode;

    @Id
    @GeneratedValue
    private Integer issueIndex;

    @ManyToOne
    @NotAudited
    @JoinColumn(name = "project_code", insertable = false, updatable = false)
    private ProjectPersistent project;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "status_code")
    @Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
    private StatusPersistent status;

    @Column(length = 120)
    private String summary;

    @Lob
    private String description;

    @Enumerated(EnumType.STRING)
    private IssueType type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
    private UserPersistent assignee;

    IssuePersistent(
            ProjectCode projectCode,
            StatusPersistent status,
            IssueType type,
            Summary summary,
            Description description) {
        this.projectCode = projectCode.value();
        this.status = status;
        this.summary = summary.value();
        this.type = type;
        this.description = description.value();
        this.assignee = null;
    }

    IssuePersistent(
            ProjectCode projectCode,
            StatusPersistent status,
            Integer issueIndex,
            IssueType type,
            Summary summary,
            Description description,
            UserPersistent assignee) {
        this.projectCode = projectCode.value();
        this.status = status;
        this.issueIndex = issueIndex;
        this.summary = summary.value();
        this.type = type;
        this.description = description.value();
        this.assignee = assignee;
    }

    public Issue toIssue() {
        var assigneeUserId = assignee == null ? null : new UserId(assignee.getUserId());
        return Issue.builder()
                .issueId(new IssueId(new ProjectCode(projectCode), issueIndex))
                .statusCode(new StatusCode(status.getCode()))
                .type(type)
                .summary(new Summary(summary))
                .description(new Description(description))
                .assigneeUserId(assigneeUserId)
                .build();
    }

    public static IssuePersistent of(Issue issue, StatusPersistent status, UserPersistent assignee) {
        return new IssuePersistent(
                issue.getIssueId().projectCode(),
                status,
                issue.getIssueId().issueIndex(),
                issue.getType(),
                issue.getSummary(),
                issue.getDescription(),
                assignee);
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    static class IssuePersistentId implements Serializable {

        String projectCode;

        Integer issueIndex;

        public static IssuePersistentId of(IssueId issueId) {
            return new IssuePersistentId(
                    issueId.projectCode().value(),
                    issueId.issueIndex());
        }
    }
}
