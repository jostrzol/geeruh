package pl.edu.pw.elka.paprykaisalami.geeruh.projects.domain.models;


import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public record ProjectCode(@NotNull @Pattern(regexp = PROJECT_CODE_REGEX) String value) {

    public static final String PROJECT_CODE_REGEX = "^[A-Z]{2,5}$";

    @Override
    public String toString() {
        return value;
    }
}
