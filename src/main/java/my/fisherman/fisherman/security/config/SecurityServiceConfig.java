package my.fisherman.fisherman.security.config;

import my.fisherman.fisherman.security.application.CustomOAuth2UserService;
import my.fisherman.fisherman.security.application.JwtService;
import my.fisherman.fisherman.security.application.SecurityUserService;
import my.fisherman.fisherman.security.config.property.JwtProperties;
import my.fisherman.fisherman.user.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;

@Configuration
public class SecurityServiceConfig {

    @Bean
    public SecurityUserService securityUserService(UserRepository userRepository) {
        return new SecurityUserService(userRepository);
    }

    @Bean
    public CustomOAuth2UserService customOAuth2UserService(
            SecurityUserService securityUserService,
            DefaultOAuth2UserService defaultOAuth2UserService
    ) {
        return new CustomOAuth2UserService(defaultOAuth2UserService, securityUserService);
    }

    @Bean
    public DefaultOAuth2UserService defaultOAuth2UserService() {
        return new DefaultOAuth2UserService();
    }

    @Bean
    public JwtService jwtService(JwtProperties jwtProperties) {
        return new JwtService(jwtProperties);
    }
}
