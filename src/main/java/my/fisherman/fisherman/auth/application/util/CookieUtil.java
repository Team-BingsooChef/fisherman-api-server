package my.fisherman.fisherman.auth.application.util;

import lombok.RequiredArgsConstructor;
import my.fisherman.fisherman.security.config.property.UrlProperties;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public final class CookieUtil {

    private final UrlProperties urlProperties;

    public ResponseCookie generateCookie(String cookieName, String cookieValue) {
        return ResponseCookie.from(cookieName, cookieValue)
            .secure(true)
            .path("/")
            .httpOnly(true)
            .domain(urlProperties.origin())
            .build();
    }

    public ResponseCookie deleteCookie(String cookieName) {
        return ResponseCookie.from(cookieName)
                .secure(true)
                .path("/")
                .httpOnly(true)
                .domain(urlProperties.origin())
                .maxAge(0)
                .build();
    }
}
