package pl.edu.pw.elka.paprykaisalami.geeruh.users.adapters.persistent;

import lombok.*;
import org.hibernate.annotations.Type;
import pl.edu.pw.elka.paprykaisalami.geeruh.users.domain.models.User;
import pl.edu.pw.elka.paprykaisalami.geeruh.users.domain.models.UserId;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "Users")
public class UserPersistent {

    @Id
    @Column(name = "user_id", updatable = false, nullable = false)
    @Type(type = "org.hibernate.type.UUIDCharType")
    private UUID userId;

    @Column(unique = true)
    private String login;

    private String passwordHash;

    @Column(unique = true)
    private String email;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "second_name")
    private String secondName;

    private String surname;

    public User toUser() {
        return User.builder()
                .userId(new UserId(userId))
                .login(login)
                .passwordHash(passwordHash)
                .email(email)
                .firstName(firstName)
                .secondName(secondName)
                .surname(surname)
                .build();
    }

    public static UserPersistent of(User user) {
        return new UserPersistent(
                user.getUserId().value(),
                user.getLogin(),
                user.getPasswordHash(),
                user.getEmail(),
                user.getFirstName(),
                user.getSecondName(),
                user.getSurname()
        );
    }
}
