package my.fisherman.fisherman.security.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import my.fisherman.fisherman.auth.application.util.CookieUtil;
import my.fisherman.fisherman.security.application.JwtService;
import my.fisherman.fisherman.security.application.dto.UserPrinciple;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Slf4j
public class CustomSuccessHandler implements AuthenticationSuccessHandler {

    private final CookieUtil cookieUtil;
    private final JwtService jwtService;
    private final String frontUrl;

    public CustomSuccessHandler(
        JwtService jwtService,
        @Value("${frontend.url}") String frontUrl,
        CookieUtil cookieUtil
    ) {
        this.jwtService = jwtService;
        this.frontUrl = frontUrl;
        this.cookieUtil = cookieUtil;
    }

    @Override
    public void onAuthenticationSuccess(
        HttpServletRequest request,
        HttpServletResponse response,
        Authentication authentication
    ) throws IOException, ServletException {
        UserPrinciple info = (UserPrinciple) authentication.getPrincipal();

        String accessToken = jwtService.createAccessToken(info);
        String refreshToken = jwtService.createRefreshToken(info);

        response.setStatus(HttpServletResponse.SC_OK);
        response.addHeader(HttpHeaders.SET_COOKIE,
            cookieUtil.generateCookie("refresh_token", refreshToken).toString());
        response.addHeader(HttpHeaders.SET_COOKIE,
            cookieUtil.generateCookie("access_token", accessToken).toString());

        response.sendRedirect(
            frontUrl + "/redirect?isFreshUser=" + info.isFreshUser()
        );
    }
}