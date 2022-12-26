package pl.edu.pw.elka.paprykaisalami.geeruh.users.adapters.api;


import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class UserUpdateRequest {

    @NotBlank
    String firstName;

    String secondName;

    @NotBlank
    String surname;
}
