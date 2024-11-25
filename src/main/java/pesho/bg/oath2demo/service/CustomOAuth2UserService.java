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
        String email = oAuth2User.getAttribute("email");
        if (this.userRepository.findByEmail(email).isPresent()) {
            return oAuth2User;
        }
        String name = oAuth2User.getAttribute("name");
        this.userRepository.saveAndFlush(new User(email, name, AuthProvider.GITHUB));
        return oAuth2User;
    }

}
