package pl.edu.pw.elka.paprykaisalami.geeruh.issues.domain.models;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Issue {

    IssueId issueId;

    IssueType type;

    Summary summary;

    Description description;
}
