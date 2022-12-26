package pl.edu.pw.elka.paprykaisalami.geeruh.spring.security;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import pl.edu.pw.elka.paprykaisalami.geeruh.users.domain.ports.UserService;

@AllArgsConstructor
@Component
class PersistentUserDetailsService implements UserDetailsService {

    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = userService.getByLogin(username)
                .getOrElseThrow(() -> new UsernameNotFoundException("User not found"));

        return User.builder()
                .username(user.getLogin())
                .password(user.getPasswordHash())
                .roles("USER")
                .build();
    }

}
