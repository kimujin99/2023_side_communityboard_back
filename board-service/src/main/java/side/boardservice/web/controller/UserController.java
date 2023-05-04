package side.boardservice.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class UserController {

    @GetMapping("/login")
    public String loginPage() {
        return "html/login";
    }

    @GetMapping("/signup")
    public String signupPage() {
        return "html/signup";
    }
}
