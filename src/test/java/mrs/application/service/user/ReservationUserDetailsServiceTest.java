package mrs.application.service.user;

import mrs.MrsTestConfiguration;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.annotation.DirtiesContext;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = MrsTestConfiguration.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@DisplayName("予約ユーザーサービス")
public class ReservationUserDetailsServiceTest {
   @Nested
   @DisplayName("予約ユーザーを取得する")
   class UserInfo {
      @Autowired
      ReservationUserDetailsService reservationUserDetailsService;
      @Autowired
      UserDetailsService userDetailsService;

      @Test
      public void ユーザー情報を取得する() {
         UserDetails result = reservationUserDetailsService.loadUserByUsername("taro-yamada");

         assertEquals("taro-yamada", result.getUsername());
      }

      @Test
      @WithUserDetails("test")
      public void フィクスチャーを使ってユーザー情報を取得する() {
         UserDetails result = userDetailsService.loadUserByUsername("test");

         assertEquals("test", result.getUsername());
         assertEquals("password", result.getPassword());
      }
   }

   @Nested
   @DisplayName("認証情報")
   class AuthentiactionInfo {
      @Test
      @WithMockUser(username = "test2", roles = {"USER", "ADMIN"})
      public void モックを使って認証情報を取得する() {
         Authentication auth = SecurityContextHolder.getContext().getAuthentication();

         assertEquals("test2", auth.getName());
         assertEquals("password", auth.getCredentials());
         assertEquals("ROLE_ADMIN", auth.getAuthorities().toArray()[0].toString());
         assertEquals("ROLE_USER", auth.getAuthorities().toArray()[1].toString());
      }
   }
}
