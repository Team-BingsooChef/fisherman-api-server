package my.fisherman.fisherman.security.config.property;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "url")
public record UrlProperties(
    String frontend,
    String[] allowedOrigins
) {

}
