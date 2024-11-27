package pesho.bg.oath2demo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestMapping;
import pesho.bg.oath2demo.service.UserService;

@Controller
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/profile")
    public String profile(@AuthenticationPrincipal Object object, Model model) {
        model.addAttribute("user", this.userService.getProfileData(object));
        return "profile";
    }

}

