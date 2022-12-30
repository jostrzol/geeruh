package pl.edu.pw.elka.paprykaisalami.geeruh.support;

import pl.edu.pw.elka.paprykaisalami.geeruh.users.domain.ports.UserPasswordEncoder;

public class FakePasswordEncoder implements UserPasswordEncoder {
    @Override
    public String encode(String password) {
        return "ENCODED:" + password;
    }
}
