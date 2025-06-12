package my.fisherman.fisherman.security.util;

import static lombok.AccessLevel.PRIVATE;

import java.util.Optional;
import lombok.NoArgsConstructor;
import my.fisherman.fisherman.security.filter.token.JwtAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@NoArgsConstructor(access = PRIVATE)
public class SecurityUtil {

    public static Optional<Long> getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication instanceof JwtAuthenticationToken) {
            return Optional.of((Long) authentication.getDetails());
        }
        return Optional.empty();
    }
}
