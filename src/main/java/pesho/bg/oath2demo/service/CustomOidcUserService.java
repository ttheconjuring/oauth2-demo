package pesho.bg.oath2demo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;
import pesho.bg.oath2demo.entity.AuthProvider;
import pesho.bg.oath2demo.entity.User;
import pesho.bg.oath2demo.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class CustomOidcUserService extends OidcUserService {

    private final UserRepository userRepository;

    @Override
    public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {
        OidcUser oidcUser = super.loadUser(userRequest);
        String email = oidcUser.getAttribute("email");
        if (this.userRepository.findByEmail(email).isPresent()) {
            return oidcUser;
        }
        String name = oidcUser.getAttribute("name");
        this.userRepository.saveAndFlush(new User(email, name, AuthProvider.GOOGLE));
        return oidcUser;
    }

}
