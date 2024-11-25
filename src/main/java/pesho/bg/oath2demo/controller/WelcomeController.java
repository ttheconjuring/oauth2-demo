package pesho.bg.oath2demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WelcomeController {

    @GetMapping("/")
    public String helloWorld() {
        return "Hello World";
    }

}
