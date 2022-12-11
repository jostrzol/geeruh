package pl.edu.pw.elka.paprykaisalami.geeruh.projects.domain.models;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Builder
@Getter
public class Project {

    @Valid
    @NotNull
    private ProjectCode projectCode;

    @NotBlank
    @Setter
    private String name;

    @NotNull
    @Setter
    private String description;
}
