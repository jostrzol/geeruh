package pl.edu.pw.elka.paprykaisalami.geeruh.issues.domain.models;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.util.Date;

@Builder
@Getter
public class IssueHistoryEntry {

    Issue historicIssue;

    Date timestamp;

    IssueHistoryEntryType type;

}
