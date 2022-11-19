package pl.edu.pw.elka.paprykaisalami.geeruh.issues.adapters.api;

import lombok.AllArgsConstructor;
import lombok.Getter;
import pl.edu.pw.elka.paprykaisalami.geeruh.issues.domain.models.IssueType;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;;

@AllArgsConstructor
@Getter
class IssueRequest {

    @NotNull
    IssueType type;

    @NotBlank
    String summary;

    String description;
}
