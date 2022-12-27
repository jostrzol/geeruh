package pl.edu.pw.elka.paprykaisalami.geeruh.users.domain.models;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Builder
@Getter
public class User {

    @Valid
    @NotNull
    private UserId userId;

    @NotBlank
    @Setter
    private String login;

    @NotBlank
    private String passwordHash;

    @NotBlank
    @Email
    @Setter
    private String email;

    @NotBlank
    @Setter
    private String firstName;

    @Setter
    private String secondName;

    @NotNull
    @Setter
    private String surname;
}
