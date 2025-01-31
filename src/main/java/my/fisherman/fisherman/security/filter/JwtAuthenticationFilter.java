package my.fisherman.fisherman.security.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import my.fisherman.fisherman.security.application.JwtService;
import my.fisherman.fisherman.security.filter.token.JwtAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

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
        if (hasNoTokenHeader(request)) {
            filterChain.doFilter(request, response);
            return;
        }

        String accessToken = getAccessToken(request);
        if (jwtService.isInvalidAccessToken(accessToken)) {
            filterChain.doFilter(request, response);
            return;
        }

        setAuthentication(accessToken);
        log.info("인증 완료: {}", SecurityContextHolder.getContext().getAuthentication().getName());
        filterChain.doFilter(request, response);

        log.info("인증 완료: {}", SecurityContextHolder.getContext().getAuthentication().getName());
    }

    private boolean hasNoTokenHeader(HttpServletRequest request) {
        return hasNoAuthorizationHeader(request) || hasNoBearerToken(request);
    }

    private boolean hasNoAuthorizationHeader(HttpServletRequest request) {
        return request.getHeader("Authorization") == null;
    }

    private boolean hasNoBearerToken(HttpServletRequest request) {
        return !request.getHeader("Authorization").startsWith("Bearer ");
    }

    private String getAccessToken(HttpServletRequest request) {
        return request.getHeader("Authorization").substring(7);
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
