package pl.edu.pw.elka.paprykaisalami.geeruh.projects.adapters.api;


import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ProjectRequest {

    @NotBlank
    String name;

    String description = "";
}
