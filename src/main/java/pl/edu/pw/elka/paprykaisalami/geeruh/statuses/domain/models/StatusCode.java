package pl.edu.pw.elka.paprykaisalami.geeruh.statuses.domain.models;


import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public record StatusCode(@NotNull @Pattern(regexp = STATUS_CODE_REGEX) String value) {

    public static final String STATUS_CODE_REGEX = "^[A-Z]{2,8}$";

    @Override
    public String toString() {
        return value;
    }
}
