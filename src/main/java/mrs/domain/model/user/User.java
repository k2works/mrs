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

    public User(String userId, String password, String firstName, String lastName, RoleName roleName) {
        //TODO 値オブジェクトに置き換える
        this.userId = userId;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
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
}
