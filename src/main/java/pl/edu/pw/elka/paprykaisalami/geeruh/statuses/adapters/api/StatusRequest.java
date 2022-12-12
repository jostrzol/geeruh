package pl.edu.pw.elka.paprykaisalami.geeruh.statuses.adapters.api;


import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class StatusRequest {

    @NotBlank
    String name;

    Integer orderNumber;
}
