package my.fisherman.fisherman.security.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import my.fisherman.fisherman.auth.application.util.CookieUtil;
import my.fisherman.fisherman.security.application.JwtService;
import my.fisherman.fisherman.security.config.property.UrlProperties;
import my.fisherman.fisherman.security.handler.CustomFailureHandler;
import my.fisherman.fisherman.security.handler.NormalLoginSuccessHandler;
import my.fisherman.fisherman.security.handler.OAuthLoginSuccessHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class SecurityHandlerConfig {

    @Bean
    public CustomFailureHandler customFailureHandler() {
        return new CustomFailureHandler();
    }

    @Bean
    public NormalLoginSuccessHandler customSuccessHandler(
        JwtService jwtService,
        ObjectMapper objectMapper,
        CookieUtil cookieUtil
    ) {
        return new NormalLoginSuccessHandler(jwtService, objectMapper, cookieUtil);
    }

    @Bean
    public OAuthLoginSuccessHandler oAuthLoginSuccessHandler(
        CookieUtil cookieUtil,
        UrlProperties urlProperties,
        JwtService jwtService
    ) {
        return new OAuthLoginSuccessHandler(cookieUtil, urlProperties.frontend(), jwtService);
    }
}
