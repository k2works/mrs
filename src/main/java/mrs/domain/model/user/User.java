package mrs.domain.model.user;

import java.io.Serializable;

/**
 * ユーザー
 */
public class User implements Serializable {
    private UserId userId;

    private Password password;

    private Name name;

    private RoleName roleName;

    public User() {
    }

    public User(UserId userId, Password password, Name name, RoleName roleName) {
        this.userId = userId;
        this.password = password;
        this.name = name;
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
        return name;
    }
}
