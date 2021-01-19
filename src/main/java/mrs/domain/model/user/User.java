package mrs.domain.model.user;

import javax.persistence.Id;
import java.io.Serializable;

/**
 * ユーザー
 */
public class User implements Serializable {
    @Id
    private UserId userId;

    private Password password;

    private String firstName;

    private String lastName;

    private RoleName roleName;

    public User() {
    }

    public User(UserId userId, Password password, Name name, RoleName roleName) {
        this.userId = userId;
        this.password = password;
        this.firstName = name.firstName;
        this.lastName = name.lastName;
        this.roleName = roleName;
    }

    public UserId userId() {
        return userId;
    }

    public Password password() {
        return password;
    }

    public RoleName roleName() {
        return roleName;
    }

    public Name name() {
        return new Name(firstName, lastName);
    }
}
