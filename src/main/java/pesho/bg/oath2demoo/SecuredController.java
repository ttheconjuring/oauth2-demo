package pesho.bg.oath2demoo;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1/secured")
public class SecuredController {

    @GetMapping("/user-info")
    public ResponseEntity<String> secured(Principal principal) {
        return ResponseEntity.ok(principal.getName());
    }

}
