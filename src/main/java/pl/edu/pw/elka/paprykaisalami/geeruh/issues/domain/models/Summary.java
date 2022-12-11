package pl.edu.pw.elka.paprykaisalami.geeruh.issues.domain.models;

import javax.validation.constraints.NotBlank;

public record Summary(@NotBlank String value) {
}
