package my.fisherman.fisherman.security.config;

import java.util.Arrays;
import lombok.RequiredArgsConstructor;
import my.fisherman.fisherman.security.application.JwtService;
import my.fisherman.fisherman.security.config.property.UrlProperties;
import my.fisherman.fisherman.security.filter.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityFilterConfig {

    private final UrlProperties urlProperties;

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter(JwtService jwtService) {
        return new JwtAuthenticationFilter(jwtService);
    }

    @Bean
    public CorsFilter corsFilter() {
        var source = new UrlBasedCorsConfigurationSource();
        var config = createCorsConfiguration(urlProperties.allowedOrigins());
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }

    private CorsConfiguration createCorsConfiguration(String... allowedOriginPatterns) {
        var config = new CorsConfiguration();
        config.setAllowCredentials(true);
        Arrays.stream(allowedOriginPatterns).forEach(config::addAllowedOriginPattern);

        // 공통 헤더 설정
        Arrays.asList(
            "Origin", "Accept", "X-Requested-With", "Content-Type",
            "Access-Control-Request-Method", "Access-Control-Request-Headers",
            "Authorization"
        ).forEach(config::addAllowedHeader);

        // 공통 메소드 설정
        Arrays.asList(
            "GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH", "HEAD"
        ).forEach(config::addAllowedMethod);

        config.setMaxAge(3600L);

        return config;
    }
}
