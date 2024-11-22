package pesho.bg.oath2demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class UserController {

    @GetMapping("/")
    public ResponseEntity<Principal> info(Principal principal) {
        return ResponseEntity.ok(principal);
    }

}