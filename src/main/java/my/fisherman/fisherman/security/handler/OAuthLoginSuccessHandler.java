package my.fisherman.fisherman.security.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import my.fisherman.fisherman.auth.application.util.CookieUtil;
import my.fisherman.fisherman.security.application.JwtService;
import my.fisherman.fisherman.security.application.dto.UserPrinciple;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

public class OAuthLoginSuccessHandler implements AuthenticationSuccessHandler {

    private final CookieUtil cookieUtil;
    private final String frontUrl;
    private final JwtService jwtService;

    public OAuthLoginSuccessHandler(
        CookieUtil cookieUtil,
        @Value("${frontend.url}") String frontUrl,
        JwtService jwtService
    ) {
        this.cookieUtil = cookieUtil;
        this.frontUrl = frontUrl;
        this.jwtService = jwtService;
    }

    @Override
    public void onAuthenticationSuccess(
        HttpServletRequest request, HttpServletResponse response,
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
