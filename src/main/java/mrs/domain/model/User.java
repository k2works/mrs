package mrs.domain.model;

import javax.persistence.*;
import java.io.Serializable;

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

    public RoleName roleName() {
        return roleName;
    }

    public String userId() {
        return userId;
    }
}
