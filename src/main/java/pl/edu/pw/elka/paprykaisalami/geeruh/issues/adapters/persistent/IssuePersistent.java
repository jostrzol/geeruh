package pl.edu.pw.elka.paprykaisalami.geeruh.issues.adapters.persistent;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.EqualsAndHashCode;
import lombok.ToString;
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
    @SequenceGenerator(name = "issue_index_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "issue_index_seq")
    @Column(name = "issue_index")
    private Integer issueIndex;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_code", insertable = false, updatable = false)
    private ProjectPersistent project;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "status_code")
    @Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
    private StatusPersistent status;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "related_issues",
            joinColumns = {
                    @JoinColumn(referencedColumnName = "project_code", name = "related_issue_project_code"),
                    @JoinColumn(referencedColumnName = "issue_index", name = "related_issue_index")
            },
            inverseJoinColumns = {
                    @JoinColumn(referencedColumnName = "project_code", name = "related_issue_project_code_child"),
                    @JoinColumn(referencedColumnName = "issue_index", name = "related_issue_index_child")
            }
    )
    @NotAudited
    private Set<IssuePersistent> relatedIssues = new HashSet<>();

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToMany(mappedBy = "relatedIssues", fetch = FetchType.LAZY)
    @NotAudited
    private Set<IssuePersistent> relatedIssuesChildren = new HashSet<>();

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
            ProjectPersistent project,
            StatusPersistent status,
            IssueType type,
            Summary summary,
            Description description) {
        this.project = project;
        this.status = status;
        this.summary = summary.value();
        this.type = type;
        this.description = description.value();
        this.assignee = null;
    }

    IssuePersistent(
            ProjectPersistent project,
            StatusPersistent status,
            Integer issueIndex,
            IssueType type,
            Summary summary,
            Description description,
            UserPersistent assignee,
            Set<IssuePersistent> relatedIssues) {
        this.project = project;
        this.status = status;
        this.issueIndex = issueIndex;
        this.summary = summary.value();
        this.type = type;
        this.description = description.value();
        this.assignee = assignee;
        this.relatedIssues = relatedIssues;
    }

    public IssueId getIssueId() {
        return new IssueId(new ProjectCode(project.getCode()), issueIndex);
    }

    public Issue toIssue() {
        var assigneeUserId = assignee == null ? null : new UserId(assignee.getUserId());
        return Issue.builder()
                .issueId(new IssueId(new ProjectCode(project.getCode()), issueIndex))
                .statusCode(new StatusCode(status.getCode()))
                .type(type)
                .summary(new Summary(summary))
                .description(new Description(description))
                .assigneeUserId(assigneeUserId)
                .relatedIssues(relatedIssues
                        .stream()
                        .map(IssuePersistent::getIssueId)
                        .collect(Collectors.toSet())
                )
                .relatedIssuesChildren(relatedIssuesChildren
                        .stream()
                        .map(IssuePersistent::getIssueId)
                        .collect(Collectors.toSet())
                )
                .build();
    }

    public static IssuePersistent of(Issue issue, ProjectPersistent project, StatusPersistent status, UserPersistent assignee, Set<IssuePersistent> relatedIssues) {
        return new IssuePersistent(
                project,
                status,
                issue.getIssueId().issueIndex(),
                issue.getType(),
                issue.getSummary(),
                issue.getDescription(),
                assignee,
                relatedIssues);
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class IssuePersistentId implements Serializable {

        String project;

        Integer issueIndex;

        public static IssuePersistentId of(IssueId issueId) {
            return new IssuePersistentId(
                    issueId.projectCode().value(),
                    issueId.issueIndex());
        }
    }
}
