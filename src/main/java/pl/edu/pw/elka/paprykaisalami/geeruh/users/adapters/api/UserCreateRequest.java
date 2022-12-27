package pl.edu.pw.elka.paprykaisalami.geeruh.users.adapters.api;


import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class UserCreateRequest {

    @NotBlank
    String login;

    @NotBlank
    String password;

    @Email
    @NotBlank
    String email;

    @NotBlank
    String firstName;

    String secondName;

    @NotBlank
    String surname;
}
