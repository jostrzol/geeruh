package pl.edu.pw.elka.paprykaisalami.geeruh.issues.adapters.api;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
class IssueRelateRequest {

    @NotBlank
    String relatedIssueId;
}
