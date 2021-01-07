package mrs.domain.model.user;

import javax.persistence.*;
import java.io.Serializable;

/**
 * ユーザー
 */
@Entity
@Table(name="usr")
public class User implements Serializable {
    @Id
    private String userId;

    private String password;

    private String firstName;

    private String lastName;

    @Enumerated(EnumType.STRING)
    private RoleName roleName;

    public User() {
    }

    public User(UserId userId, Password password, Name name, RoleName roleName) {
        this.userId = userId.value;
        this.password = password.value;
        this.firstName = name.firstName;
        this.lastName = name.lastName;
        this.roleName = roleName;
    }

    public String userId() {
        return userId;
    }

    public String password() {
        return password;
    }

    public String firstName() {
        return firstName;
    }

    public String lastName() {
        return lastName;
    }

    public RoleName roleName() {
        return roleName;
    }

    public Name name() {
        return new Name(firstName, lastName);
    }
}
