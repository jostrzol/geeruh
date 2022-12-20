package pl.edu.pw.elka.paprykaisalami.geeruh.statuses.adapters.api;


import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class StatusRequest {

    @NotBlank
    String name;

    @NotNull
    Integer orderNumber;
}
