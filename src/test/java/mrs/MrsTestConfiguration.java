package mrs;

import mrs.application.service.user.ReservationUserDetails;
import mrs.domain.model.user.*;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@TestConfiguration
public class MrsTestConfiguration {
    @Bean
    @Primary
    public UserDetailsService userDetailsService() {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
                User user = new User(
                        new UserId(userId),
                        new Password("password"),
                        new Name("山田", "太郎"),
                        RoleName.ADMIN
                );
                return new ReservationUserDetails(user);
            }
        };
    }
}
