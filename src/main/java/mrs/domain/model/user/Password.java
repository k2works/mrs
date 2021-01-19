package mrs.domain.model.user;

/**
 * パスワード
 */
public class Password {
    String value;

    public Password(String value) {
        this.value = value;
    }

    public Password() {
    }

    public String value() {
        return value;
    }
}
