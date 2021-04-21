package mrs.presentation;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * ホーム画面
 */
@Controller
public class HomeController {
    @RequestMapping("/")
    String home() {
        return "forward:/index.html";
    }
}
