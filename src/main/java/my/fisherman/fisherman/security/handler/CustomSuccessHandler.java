package my.fisherman.fisherman.security.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import my.fisherman.fisherman.security.application.JwtService;
import my.fisherman.fisherman.security.application.dto.UserPrinciple;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Slf4j
public class CustomSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtService jwtService;
    private final String frontUrl;

    public CustomSuccessHandler(
        JwtService jwtService,
        @Value("${frontend.url}") String frontUrl
    ) {
        this.jwtService = jwtService;
        this.frontUrl = frontUrl;
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
        response.setStatus(HttpServletResponse.SC_OK);
        response.sendRedirect(
            frontUrl +
                "/access_token=" + accessToken +
                "&refresh_token=" + refreshToken +
                "&is_fresh_user=" + info.isFreshUser()
        );
    }
}