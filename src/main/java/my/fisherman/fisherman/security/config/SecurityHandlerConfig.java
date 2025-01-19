package my.fisherman.fisherman.security.config;

import my.fisherman.fisherman.security.application.JwtService;
import my.fisherman.fisherman.security.handler.CustomFailureHandler;
import my.fisherman.fisherman.security.handler.CustomSuccessHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SecurityHandlerConfig {

    @Bean
    public CustomFailureHandler customFailureHandler() {
        return new CustomFailureHandler();
    }

    @Bean
    public CustomSuccessHandler customSuccessHandler(JwtService jwtService) {
        return new CustomSuccessHandler(jwtService);
    }
}
