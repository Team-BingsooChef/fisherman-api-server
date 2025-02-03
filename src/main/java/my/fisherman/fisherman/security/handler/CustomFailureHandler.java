package my.fisherman.fisherman.security.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

public class CustomFailureHandler
        implements AuthenticationFailureHandler, AuthenticationEntryPoint, AccessDeniedHandler {
    @Override
    public void onAuthenticationFailure(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException exception
    ) throws IOException, ServletException {
        //TODO: 인증 실패 응답 처리
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    }

    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException authException
    ) throws IOException, ServletException {
        //TODO: 인증이 필요한 리소스에 접근할 때 처리
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    }

    @Override
    public void handle(
            HttpServletRequest request,
            HttpServletResponse response,
            AccessDeniedException accessDeniedException
    ) throws IOException, ServletException {
        //TODO: 권한이 없는 리소스에 접근할 때 처리
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    }
}
