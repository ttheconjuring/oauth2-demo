package pesho.bg.oath2demo.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import pesho.bg.oath2demo.service.CustomOAuth2UserService;
import pesho.bg.oath2demo.service.CustomOidcUserService;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    // When you configure Google as an OAuth2 provider in Spring Security, it defaults to OIDC (openid-connect).
    private final CustomOidcUserService customOidcUserService;
    // GitHub does not support OIDC, so Spring Security uses DefaultOAuth2UserService for fetching user details.
    private final CustomOAuth2UserService customOAuth2UserService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(authorize -> authorize
                        .anyRequest().authenticated())
                .formLogin(Customizer.withDefaults())
                .oauth2Login(oauth2 -> oauth2
                        .userInfoEndpoint(userInfo -> userInfo
                                .oidcUserService(this.customOidcUserService)
                                .userService(this.customOAuth2UserService)))
                .build();
    }

}
