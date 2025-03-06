package my.fisherman.fisherman.security.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import my.fisherman.fisherman.global.exception.AuthErrorCode;
import my.fisherman.fisherman.global.exception.FishermanException;
import my.fisherman.fisherman.security.application.JwtService;
import my.fisherman.fisherman.security.filter.token.JwtAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final String ACCESS_TOKEN_COOKIE = "access_token";
    private final JwtService jwtService;

    public JwtAuthenticationFilter(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(
        HttpServletRequest request,
        HttpServletResponse response,
        FilterChain filterChain
    ) throws ServletException, IOException {
        if (hasNoAccessCookie(request)) {
            filterChain.doFilter(request, response);
            return;
        }

        String accessToken = getAccessToken(request);
        if (jwtService.isInvalidAccessToken(accessToken)) {
            filterChain.doFilter(request, response);
            return;
        }

        setAuthentication(accessToken);
        filterChain.doFilter(request, response);
    }

    private boolean hasNoAccessCookie(HttpServletRequest request) {
        if (request.getCookies() == null) {
            return true;
        }
        return Arrays.stream(request.getCookies())
            .noneMatch(cookie -> ACCESS_TOKEN_COOKIE.equals(cookie.getName()));
    }

    private String getAccessToken(HttpServletRequest request) {
        return Arrays.stream(request.getCookies())
            .filter(cookie -> ACCESS_TOKEN_COOKIE.equals(cookie.getName()))
            .findFirst()
            .orElseThrow(() -> new FishermanException(AuthErrorCode.INVALID_TOKEN))
            .getValue();
    }

    private void setAuthentication(String accessToken) {
        var id = jwtService.getIdFromAccessToken(accessToken);
        List<GrantedAuthority> role = List.of(
            new SimpleGrantedAuthority(jwtService.getRoleFromAccessToken(accessToken))
        );
        var authentication = new JwtAuthenticationToken(id, role);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}