package pl.edu.pw.elka.paprykaisalami.geeruh.statuses.domain.models;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Builder
@Getter
public class Status {

    @Valid
    @NotNull
    private StatusCode statusCode;

    @NotBlank
    @Setter
    private String name;

    @NotNull
    @Setter
    private Integer orderNumber;
}
