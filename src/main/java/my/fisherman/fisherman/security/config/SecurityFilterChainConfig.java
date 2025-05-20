package my.fisherman.fisherman.security.config;

import lombok.RequiredArgsConstructor;
import my.fisherman.fisherman.security.application.CustomOAuth2UserService;
import my.fisherman.fisherman.security.application.SecurityUserService;
import my.fisherman.fisherman.security.filter.JwtAuthenticationFilter;
import my.fisherman.fisherman.security.handler.CustomFailureHandler;
import my.fisherman.fisherman.security.handler.CustomSuccessHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityFilterChainConfig {

    private final CustomOAuth2UserService customOauth2UserService;
    private final SecurityUserService securityUserService;
    private final CustomSuccessHandler customSuccessHandler;
    private final CustomFailureHandler customFailureHandler;

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final CorsFilter corsFilter;

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
                .loginProcessingUrl("/login")
                .usernameParameter("email")
            ).userDetailsService(securityUserService)
            .oauth2Login((oauth2Login) -> oauth2Login
                .userInfoEndpoint(userInfoEndpoint -> userInfoEndpoint
                    .userService(customOauth2UserService))
                .successHandler(customSuccessHandler)
                .failureHandler(customFailureHandler)
                .authorizationEndpoint(endpoint -> endpoint
                    .baseUri("/oauth2/authorize")
                )
                .redirectionEndpoint(endpoint -> endpoint
                    .baseUri("/oauth2/callback/*")
                )
            )
            .exceptionHandling((exceptionHandling) -> exceptionHandling
                .authenticationEntryPoint(customFailureHandler)
                .accessDeniedHandler(customFailureHandler)
            )
        ;

        http.addFilterBefore(corsFilter, UsernamePasswordAuthenticationFilter.class);
        http.addFilterAfter(jwtAuthenticationFilter, CorsFilter.class);

        return http.build();
    }
}
