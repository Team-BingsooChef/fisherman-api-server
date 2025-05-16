package my.fisherman.fisherman.auth.application;

import java.util.List;
import lombok.RequiredArgsConstructor;
import my.fisherman.fisherman.auth.application.dto.Token;
import my.fisherman.fisherman.auth.application.util.CodeGenerator;
import my.fisherman.fisherman.auth.application.util.CookieUtil;
import my.fisherman.fisherman.auth.domain.Authentication;
import my.fisherman.fisherman.auth.repository.AuthenticationRepository;
import my.fisherman.fisherman.auth.repository.RefreshTokenRepository;
import my.fisherman.fisherman.global.exception.AuthErrorCode;
import my.fisherman.fisherman.global.exception.FishermanException;
import my.fisherman.fisherman.global.util.MailUtil;
import my.fisherman.fisherman.global.util.ThymeleafUtil;
import my.fisherman.fisherman.security.application.JwtService;
import my.fisherman.fisherman.security.application.dto.UserPrinciple;
import my.fisherman.fisherman.user.domain.User;
import my.fisherman.fisherman.user.repository.UserRepository;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final Integer AUTH_CODE_LENGTH = 6;
    private final String AUTH_CODE_SUBJECT = "Fisherman 인증 코드";

    private final ThymeleafUtil thymeleafUtil;
    private final MailUtil mailUtil;

    private final AuthenticationRepository authenticationRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final CookieUtil cookieUtil;

    public void sendAuthCode(String email) {
        List<Integer> authCode = CodeGenerator.generateCode(AUTH_CODE_LENGTH);
        String mailContext = thymeleafUtil.createAuthCodeContext(authCode);

        mailUtil.sendMail(email, mailContext, AUTH_CODE_SUBJECT);

        Authentication authentication = new Authentication(email, authCode);
        authenticationRepository.save(email, authentication);
    }

    public void verifyAuthCode(String email, String authCode) {
        Authentication authentication = authenticationRepository.find(email)
            .orElseThrow(() -> new FishermanException(AuthErrorCode.INVALID_AUTH_REQUEST));

        authentication.verify(authCode);
        authenticationRepository.save(email, authentication);

    }

    public Token refreshToken(String refreshToken) {
        Long userId = refreshTokenRepository.find(refreshToken)
            .orElseThrow(() -> new FishermanException(AuthErrorCode.INVALID_REFRESH_TOKEN));
        refreshTokenRepository.delete(refreshToken);

        User user = userRepository.findById(userId)
            .orElseThrow(() -> new FishermanException(AuthErrorCode.INVALID_REFRESH_TOKEN));
        UserPrinciple userPrincipal = UserPrinciple.from(user);

        String accessToken = jwtService.createAccessToken(userPrincipal);
        String newRefreshToken = jwtService.createRefreshToken(userPrincipal);

        ResponseCookie accessCookie = cookieUtil.generateCookie("access_token", accessToken);
        ResponseCookie refreshCookie = cookieUtil.generateCookie("refresh_token", newRefreshToken);

        return new Token(accessCookie, refreshCookie);
    }

    @Transactional
    public void withdraw(String refreshToken) {
        Long userId = refreshTokenRepository.find(refreshToken)
            .orElseThrow(() -> new FishermanException(AuthErrorCode.INVALID_REFRESH_TOKEN));
        refreshTokenRepository.delete(refreshToken);

        User user = userRepository.findById(userId)
            .orElseThrow(() -> new FishermanException(AuthErrorCode.INVALID_REFRESH_TOKEN));

        userRepository.delete(user);
    }

    public Token logout(String refreshToken) {
        refreshTokenRepository.delete(refreshToken);

        ResponseCookie accessCookie = cookieUtil.deleteCookie("access_token");
        ResponseCookie refreshCookie = cookieUtil.deleteCookie("refresh_token");
        return new Token(accessCookie, refreshCookie);

    }
}
