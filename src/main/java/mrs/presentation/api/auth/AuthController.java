package mrs.presentation.api.auth;

import mrs.application.service.auth.AuthService;
import mrs.domain.model.user.Name;
import mrs.domain.model.user.Password;
import mrs.domain.model.user.User;
import mrs.domain.model.user.UserId;
import mrs.infrastructure.payload.request.LoginRequest;
import mrs.infrastructure.payload.request.SignupRequest;
import mrs.infrastructure.payload.response.JwtResponse;
import mrs.infrastructure.payload.response.MessageResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController("JWT認証API")
@RequestMapping("/api/auth")
public class AuthController {
    AuthenticationManager authenticationManager;
    PasswordEncoder encoder;
    AuthService authService;

    public AuthController(AuthenticationManager authenticationManager, PasswordEncoder encoder, AuthService authService) {
        this.authenticationManager = authenticationManager;
        this.encoder = encoder;
        this.authService = authService;
    }

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
        );
        JwtResponse jwtResponse = authService.createJwtResponse(authentication);

        return ResponseEntity.ok(jwtResponse);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signupRequest) {
        Optional<User> user = authService.findUserById(signupRequest.getUserId());
        if (user.isPresent()) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }

        UserId userId = new UserId(signupRequest.getUserId());
        Password password = new Password(encoder.encode(signupRequest.getPassword()));
        Name name = new Name(signupRequest.getFirstName(), signupRequest.getLastName());
        String role = signupRequest.getRole();

        authService.createUser(userId, password, name, role);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }
}
