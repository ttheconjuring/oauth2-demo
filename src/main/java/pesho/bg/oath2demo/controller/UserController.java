package pesho.bg.oath2demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/api/v1/user")
public class UserController {

    @GetMapping("/profile")
    public String profile(Principal principal, Model model) {
        model.addAttribute("name", principal.getName());
        return "profile";
    }

}

