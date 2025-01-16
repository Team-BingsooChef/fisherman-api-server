package my.fisherman.fisherman.security.config;

import lombok.RequiredArgsConstructor;
import my.fisherman.fisherman.security.application.CustomOAuth2UserService;
import my.fisherman.fisherman.security.application.SecurityUserService;
import my.fisherman.fisherman.security.handler.CustomFailureHandler;
import my.fisherman.fisherman.security.handler.CustomSuccessHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityFilterChainConfig {
    private final CustomOAuth2UserService customOauth2UserService;
    private final SecurityUserService securityUserService;
    private final CustomSuccessHandler customSuccessHandler;
    private final CustomFailureHandler customFailureHandler;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .sessionManagement(sessionManagement ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                .formLogin((formLogin) -> formLogin
                        .failureHandler(customFailureHandler)
                        .successHandler(customSuccessHandler)
                        .loginProcessingUrl("/login") // TODO: 경로 설정 필요
                        .usernameParameter("email")
                )
                .userDetailsService(securityUserService)

                .oauth2Login((oauth2Login) -> oauth2Login
                        .userInfoEndpoint(userInfoEndpoint -> userInfoEndpoint
                                .userService(customOauth2UserService))
                        .successHandler(customSuccessHandler)
                        .failureHandler(customFailureHandler)
                        .authorizationEndpoint(endpoint -> endpoint
                                .baseUri("/oauth2/authorize") // TODO: 경로 설정 필요
                        )
                        .redirectionEndpoint(endpoint -> endpoint
                                .baseUri("/oauth2/callback/*")// TODO: 경로 설정 필요
                        )
                )

                .exceptionHandling((exceptionHandling) -> exceptionHandling
                        .authenticationEntryPoint(customFailureHandler)
                        .accessDeniedHandler(customFailureHandler)
                )
        ;

        return http.build();
    }
}
