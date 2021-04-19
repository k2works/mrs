package mrs.application.service.auth;

import mrs.application.repository.UserRepository;
import mrs.application.service.user.ReservationUserDetails;
import mrs.domain.model.user.*;
import mrs.infrastructure.payload.response.JwtResponse;
import mrs.infrastructure.security.jwt.JwtUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 認証・登録サービス
 */
@Service
@Transactional
public class AuthService {
    UserRepository userRepository;
    JwtUtils jwtUtils;

    public AuthService(UserRepository userRepository, JwtUtils jwtUtils) {
        this.userRepository = userRepository;
        this.jwtUtils = jwtUtils;
    }

    /**
     * 認証トークンを生成する
     */
    public JwtResponse createJwtResponse(Authentication authentication) {
        String jwt = jwtUtils.generateJwtToken(authentication);
        ReservationUserDetails userDetails = (ReservationUserDetails) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        User user = userDetails.getUser();
        return new JwtResponse(jwt, userDetails.getUsername(), roles, user);
    }

    /**
     * 利用者の登録を確認する
     */
    public Optional<User> findUserById(String userId) {
        return Optional.ofNullable(userRepository.findById(userId));
    }

    /**
     * 利用者を登録する
     */
    public void createUser(UserId userId, Password password, Name name, String role) {
        RoleName roleName;
        if (role == null) {
            roleName = RoleName.USER;
        } else {
            roleName = RoleName.valueOf(role);
        }
        User newUser = new User(userId, password, name, roleName);
        userRepository.save(newUser);
    }
}
