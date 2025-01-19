package my.fisherman.fisherman.security.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import my.fisherman.fisherman.security.application.JwtService;
import my.fisherman.fisherman.security.application.dto.UserPrinciple;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Slf4j
public class CustomSuccessHandler implements AuthenticationSuccessHandler {
    private final JwtService jwtService;

    public CustomSuccessHandler(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) throws IOException, ServletException {
        var info = (UserPrinciple) authentication.getPrincipal();

        var accessToken = jwtService.createAccessToken(info);
        var refreshToken = jwtService.createRefreshToken(info);
        //TODO: 토큰 반환 설정
    }
}