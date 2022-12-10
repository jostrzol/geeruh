package pl.edu.pw.elka.paprykaisalami.geeruh.issues.adapters.api;

import pl.edu.pw.elka.paprykaisalami.geeruh.issues.domain.models.IssueHistoryEntry;
import pl.edu.pw.elka.paprykaisalami.geeruh.issues.domain.models.IssueHistoryEntryType;

import java.util.Date;

record IssueHistoryResponse(IssueResponse historicIssue, Date timestamp, IssueHistoryEntryType type) {

    public static IssueHistoryResponse of(IssueHistoryEntry issueHistoryEntry) {
        return new IssueHistoryResponse(
                IssueResponse.of(issueHistoryEntry.getHistoricIssue()),
                issueHistoryEntry.getTimestamp(),
                issueHistoryEntry.getType()
        );
    }
}
