package pl.edu.pw.elka.paprykaisalami.geeruh.users.adapters.bcryptpasswordencoder;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import pl.edu.pw.elka.paprykaisalami.geeruh.users.domain.ports.UserPasswordEncoder;

@AllArgsConstructor
@Component
class BCryptUserPasswordEncoder implements UserPasswordEncoder {

  private PasswordEncoder passwordEncoder;

  @Override
  public String encode(String password) {
    return passwordEncoder.encode(password);
  }
}
