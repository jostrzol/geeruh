package pl.edu.pw.elka.paprykaisalami.geeruh.security;


import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Jacksonized
@Builder
@Value
public class LoginRequest {

    String username;

    String password;
}
