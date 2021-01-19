package mrs.domain.model.user;

/**
 * ユーザーID
 */
public class UserId {
    String value;

    public UserId(String value) {
        this.value = value;
    }

    public UserId() {
    }

    public String value() {
        return value;
    }
}
