package pl.edu.pw.elka.paprykaisalami.geeruh.issues.adapters.api;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
import pl.edu.pw.elka.paprykaisalami.geeruh.issues.domain.models.IssueType;

@Jacksonized
@Builder
@Value
class IssueRequest {

    IssueType type;

    String summary;

    String description;
}
