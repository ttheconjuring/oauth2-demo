package pesho.bg.oath2demo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import pesho.bg.oath2demo.entity.User;
import pesho.bg.oath2demo.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public String register(String name, String email, String password) {
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setAuthProvider(null);
        User saved = this.userRepository.saveAndFlush(user);
        return String.format("redirect:/api/v1/auth/login/%d", saved.getId());
    }

    public User getProfileData(Object object) {
        String email;
        if (object instanceof OAuth2User) {
            email = ((OAuth2User) object).getAttribute("email");
        } else if (object instanceof OidcUser) {
            email = ((OidcUser) object).getAttribute("email");
        } else {
            email = ((UserDetails) object).getUsername();
        }
        return this.userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException(String.format("User not found: %s", email)));
    }
}
