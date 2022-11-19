package pl.edu.pw.elka.paprykaisalami.geeruh.issues.adapters.persistent;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.jetbrains.annotations.Nullable;
import pl.edu.pw.elka.paprykaisalami.geeruh.issues.domain.models.Description;
import pl.edu.pw.elka.paprykaisalami.geeruh.issues.domain.models.Issue;
import pl.edu.pw.elka.paprykaisalami.geeruh.issues.domain.models.IssueId;
import pl.edu.pw.elka.paprykaisalami.geeruh.issues.domain.models.IssueType;
import pl.edu.pw.elka.paprykaisalami.geeruh.issues.domain.models.Summary;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "Issues")
class IssuePersistent {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    @Type(type = "org.hibernate.type.UUIDCharType")
    private UUID id;

    @Column(length = 120)
    private String summary;

    @Column(length = 1000)
    private String description;

    @Enumerated(EnumType.ORDINAL)
    private IssueType type;

    public Issue toIssue() {
        return Issue.builder()
                .issueId(IssueId.of(id))
                .type(type)
                .summary(Summary.of(summary))
                .description(Description.of(description))
                .build();
    }

    IssuePersistent(IssueType type, Summary summary, @Nullable Description description) {
        this.summary = summary.getValue();
        this.type = type;
        this.description = description.getValue();
    }

    IssuePersistent(Issue issue) {
        this.summary = issue.getSummary().getValue();
        this.type = issue.getType();
        if (issue.getDescription() != null) {
            this.description = issue.getDescription().getValue();
        }
    }

}
