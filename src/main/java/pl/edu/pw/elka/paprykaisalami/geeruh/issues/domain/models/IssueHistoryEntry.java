package pl.edu.pw.elka.paprykaisalami.geeruh.issues.domain.models;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Builder
@Data
public class IssueHistoryEntry {

    Issue historicIssue;

    Date timestamp;

    IssueHistoryEntryType type;

}
