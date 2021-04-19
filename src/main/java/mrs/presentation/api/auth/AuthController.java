package mrs.presentation.api.auth;

import mrs.application.repository.UserRepository;
import mrs.application.service.user.ReservationUserDetails;
import mrs.domain.model.user.*;
import mrs.infrastructure.payload.request.LoginRequest;
import mrs.infrastructure.payload.request.SignupRequest;
import mrs.infrastructure.payload.response.JwtResponse;
import mrs.infrastructure.payload.response.MessageResponse;
import mrs.infrastructure.security.jwt.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController("JWT認証API")
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        ReservationUserDetails userDetails = (ReservationUserDetails) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        User user = userDetails.getUser();

        return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getUsername(), roles, user));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signupRequest) {
        Optional<User> user = Optional.ofNullable(userRepository.findById(signupRequest.getUserId()));
        if (user.isPresent()) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }

        //Create new user's account
        UserId userId = new UserId(signupRequest.getUserId());
        Password password = new Password(encoder.encode(signupRequest.getPassword()));
        Name name = new Name(signupRequest.getUserId(),signupRequest.getUserId());
        String role = signupRequest.getRole();
        RoleName roleName;
        if (role == null) {
            roleName = RoleName.USER;
        } else {
            roleName = RoleName.valueOf(role);
        }
        User newUser = new User(userId,password,name, roleName);
        userRepository.save(newUser);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }
}
