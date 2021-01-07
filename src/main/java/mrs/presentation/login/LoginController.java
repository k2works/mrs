package mrs.presentation.login;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * ログイン画面
 */
@Controller
public class LoginController {
    @GetMapping("loginForm")
    String loginForm() {
        return "login/loginForm";
    }
}
