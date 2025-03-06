package my.fisherman.fisherman.security.config;

import lombok.RequiredArgsConstructor;
import my.fisherman.fisherman.auth.application.util.CookieUtil;
import my.fisherman.fisherman.security.application.JwtService;
import my.fisherman.fisherman.security.config.property.UrlProperties;
import my.fisherman.fisherman.security.handler.CustomFailureHandler;
import my.fisherman.fisherman.security.handler.CustomSuccessHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class SecurityHandlerConfig {

    private final UrlProperties urlProperties;

    @Bean
    public CustomFailureHandler customFailureHandler() {
        return new CustomFailureHandler();
    }

    @Bean
    public CustomSuccessHandler customSuccessHandler(
        JwtService jwtService,
        UrlProperties urlProperties,
        CookieUtil cookieUtil
    ) {
        return new CustomSuccessHandler(jwtService, urlProperties.frontend(), cookieUtil);
    }
}
