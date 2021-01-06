package mrs;

import mrs.application.service.user.ReservationUserDetails;
import mrs.domain.model.user.RoleName;
import mrs.domain.model.user.User;
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
            public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
                User user = new User();
                user.setUserId(s);
                user.setFirstName("太郎");
                user.setLastName("山田");
                user.setRoleName(RoleName.ADMIN);
                user.setPassword("password");
                return new ReservationUserDetails(user);
            }
        };
    }
}
