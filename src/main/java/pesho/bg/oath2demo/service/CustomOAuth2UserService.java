package pesho.bg.oath2demo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import pesho.bg.oath2demo.entity.AuthProvider;
import pesho.bg.oath2demo.entity.User;
import pesho.bg.oath2demo.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        String clientRegistrationId = userRequest.getClientRegistration().getRegistrationId();

        String email;
        String name;
        Integer providerId;
        AuthProvider authProvider;

        switch (clientRegistrationId) {
            case "google":
                email = oAuth2User.getAttribute("email");
                name = oAuth2User.getAttribute("name");
                providerId = oAuth2User.getAttribute("sub"); // Google-specific unique ID
                authProvider = AuthProvider.GOOGLE;
                break;

            case "github":
                email = oAuth2User.getAttribute("email");
                name = oAuth2User.getAttribute("name");
                providerId = oAuth2User.getAttribute("id"); // GitHub-specific unique ID
                authProvider = AuthProvider.GITHUB;
                break;

            default:
                throw new OAuth2AuthenticationException("Unsupported provider: " + clientRegistrationId);
        }

        // Find existing user or create a new one
        String finalEmail = email;
        String finalName = name;
        AuthProvider finalAuthProvider = authProvider;
        Integer finalProviderId = providerId;

        this.userRepository.findByEmail(email)
                .orElseGet(() -> this.userRepository.save(
                        new User(finalEmail, finalName, finalAuthProvider, finalProviderId)
                ));

        return oAuth2User;
    }

}
