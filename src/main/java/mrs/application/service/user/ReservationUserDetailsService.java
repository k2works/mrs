package mrs.application.service.user;

import mrs.application.repository.UserRepository;
import mrs.domain.model.user.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * 利用者サービス
 */
@Service
public class ReservationUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    public ReservationUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * 利用者を取得する
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findById(username);
        if (user != null) {
            throw new UsernameNotFoundException(username + " is not found.");
        }
        return new ReservationUserDetails(user);
    }
}
