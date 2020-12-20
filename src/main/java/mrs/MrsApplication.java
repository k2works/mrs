package mrs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class MrsApplication {

    @RequestMapping("/")
    String hello() {
        return "Hello World!";
    }

    public static void main(String[] args) {
        SpringApplication.run(MrsApplication.class, args);
    }

}
