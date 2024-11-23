package pesho.bg.oath2demo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
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
        this.userRepository.saveAndFlush(user);
        return "redirect:/api/v1/auth/login";
    }

    public String login(String username, String password) {
        User user = this.userRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        if (user.getAuthProvider() != null) {
            return "Invalid credentials!";
        }
        if (!passwordEncoder.matches(password, user.getPassword())) {
            return "Invalid credentials!";
        }
        // TODO: authenticate
        return "redirect:/api/v1/auth/user/profile";
    }

}
