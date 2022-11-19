package pl.edu.pw.elka.paprykaisalami.geeruh.issues.domain.models;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class IssueHistoryEntry {

    Issue historicIssue;

    Timestamp timestamp;

    IssueHistoryEntryType type;

}
