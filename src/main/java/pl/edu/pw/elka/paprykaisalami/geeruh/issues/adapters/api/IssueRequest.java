package pl.edu.pw.elka.paprykaisalami.geeruh.issues.adapters.api;

import lombok.AllArgsConstructor;
import lombok.Getter;
import pl.edu.pw.elka.paprykaisalami.geeruh.issues.domain.models.IssueType;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

@AllArgsConstructor
@Getter
class IssueRequest {

    @NotNull
    IssueType type;

    @NotBlank
    String summary;

    @Null
    String description;
}
