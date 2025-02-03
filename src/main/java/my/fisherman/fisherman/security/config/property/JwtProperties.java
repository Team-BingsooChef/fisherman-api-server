package my.fisherman.fisherman.security.config.property;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "jwt")
public record JwtProperties(
        String accessSecret,
        String refreshSecret,
        Long accessTokenExpiration,
        Long refreshTokenExpiration
) {
}