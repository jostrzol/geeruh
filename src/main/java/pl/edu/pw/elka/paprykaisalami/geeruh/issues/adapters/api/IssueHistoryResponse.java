package pl.edu.pw.elka.paprykaisalami.geeruh.issues.adapters.api;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Value;
import pl.edu.pw.elka.paprykaisalami.geeruh.issues.domain.models.IssueHistoryEntry;
import pl.edu.pw.elka.paprykaisalami.geeruh.issues.domain.models.IssueHistoryEntryType;

import java.util.Date;

@Builder(access = AccessLevel.PRIVATE)
@Value
class IssueHistoryResponse {

    IssueResponse historicIssue;

    Date timestamp;

    IssueHistoryEntryType type;

    public static IssueHistoryResponse of(IssueHistoryEntry issueHistoryEntry) {
        return IssueHistoryResponse.builder()
                .historicIssue(IssueResponse.of(issueHistoryEntry.getHistoricIssue()))
                .timestamp(issueHistoryEntry.getTimestamp().getValue())
                .type(issueHistoryEntry.getType())
                .build();
    }
}
