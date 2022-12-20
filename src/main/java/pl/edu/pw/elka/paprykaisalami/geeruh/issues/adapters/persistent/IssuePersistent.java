package pl.edu.pw.elka.paprykaisalami.geeruh.issues.adapters.persistent;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.hibernate.envers.RelationTargetAuditMode;

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
import java.io.Serializable;

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
    @JoinColumn(name = "projectCode", insertable = false, updatable = false)
    private ProjectPersistent project;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "statusCode")
    @Audited(targetAuditMode=RelationTargetAuditMode.NOT_AUDITED)
    private StatusPersistent status;

    @Column(length = 120)
    private String summary;

    @Lob
    private String description;

    @Enumerated(EnumType.STRING)
    private IssueType type;

    IssuePersistent(
            ProjectCode projectCode,
            StatusPersistent status,
            IssueType type,
            Summary summary,
            Description description
    ) {
        this.projectCode = projectCode.value();
        this.status = status;
        this.summary = summary.value();
        this.type = type;
        this.description = description.value();
    }

    IssuePersistent(
            ProjectCode projectCode,
            StatusPersistent status,
            Integer issueIndex,
            IssueType type,
            Summary summary,
            Description description
    ) {
        this.projectCode = projectCode.value();
        this.status = status;
        this.issueIndex = issueIndex;
        this.summary = summary.value();
        this.type = type;
        this.description = description.value();
    }

    public Issue toIssue() {
        return Issue.builder()
                .issueId(new IssueId(new ProjectCode(projectCode), issueIndex))
                .statusCode(new StatusCode(status.getCode()))
                .type(type)
                .summary(new Summary(summary))
                .description(new Description(description))
                .build();
    }

    public static IssuePersistent of(Issue issue, StatusPersistent status) {
        return new IssuePersistent(
                issue.getIssueId().projectCode(),
                status,
                issue.getIssueId().issueIndex(),
                issue.getType(),
                issue.getSummary(),
                issue.getDescription()
        );
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
                    issueId.issueIndex()
            );
        }
    }
}
