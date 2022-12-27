package pl.edu.pw.elka.paprykaisalami.geeruh.users.domain.ports;

public interface UserPasswordEncoder {

    String encode(String password);
}
