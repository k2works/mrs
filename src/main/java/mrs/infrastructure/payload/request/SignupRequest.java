package mrs.infrastructure.payload.request;

import javax.validation.constraints.*;

public class SignupRequest {
    @NotBlank
    @Size(min = 4, max = 20)
    private String userId;

    private String role;

    @NotBlank
    @Size(min = 6, max = 40)
    private String password;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return this.role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
