package pl.edu.pw.elka.paprykaisalami.geeruh.issues.adapters.api;

import lombok.Data;
import pl.edu.pw.elka.paprykaisalami.geeruh.issues.domain.models.IssueType;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
class IssueRequest {

    @NotNull
    IssueType type;

    @NotBlank
    String summary;

    String description = "";
}
