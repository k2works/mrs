package mrs.infrastructure.payload.response;

import mrs.domain.model.user.User;

import java.util.List;

public class JwtResponse {
    private String token;
    private String type = "Bearer";
    private final List<String> roles;
    private String userid;
    private final User user;

    public JwtResponse(String accessToken, String userid, List<String> roles, User user) {
        this.token = accessToken;
        this.userid = userid;
        this.roles = roles;
        this.user = user;
    }

    public String getAccessToken() {
        return token;
    }

    public void setAccessToken(String accessToken) {
        this.token = accessToken;
    }

    public String getTokenType() {
        return type;
    }

    public void setTokenType(String tokenType) {
        this.type = tokenType;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public List<String> getRoles() {
        return roles;
    }
}
