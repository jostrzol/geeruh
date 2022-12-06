package pl.edu.pw.elka.paprykaisalami.geeruh.issues.adapters.persistent;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.hibernate.envers.Audited;
import pl.edu.pw.elka.paprykaisalami.geeruh.issues.domain.models.Description;
import pl.edu.pw.elka.paprykaisalami.geeruh.issues.domain.models.Issue;
import pl.edu.pw.elka.paprykaisalami.geeruh.issues.domain.models.IssueId;
import pl.edu.pw.elka.paprykaisalami.geeruh.issues.domain.models.IssueType;
import pl.edu.pw.elka.paprykaisalami.geeruh.issues.domain.models.Summary;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Audited
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

    @Lob
    private String description;

    @Enumerated(EnumType.STRING)
    private IssueType type;

    public Issue toIssue() {
        return Issue.builder()
                .issueId(IssueId.of(id))
                .type(type)
                .summary(Summary.of(summary))
                .description(Description.of(description))
                .build();
    }

    IssuePersistent(IssueType type, Summary summary, Description description) {
        this.summary = summary.getValue();
        this.type = type;
        this.description = description.getValue();
    }

    public static IssuePersistent of(Issue issue) {
        return new IssuePersistent(
                issue.getIssueId().getValue(),
                issue.getSummary().getValue(),
                issue.getDescription().getValue(),
                issue.getType()
        );
    }
}
