package my.fisherman.fisherman.auth.application;

import lombok.RequiredArgsConstructor;
import my.fisherman.fisherman.auth.application.util.CodeGenerator;
import my.fisherman.fisherman.auth.domain.Authentication;
import my.fisherman.fisherman.auth.repository.AuthenticationRepository;
import my.fisherman.fisherman.global.exception.FishermanException;
import my.fisherman.fisherman.global.exception.code.AuthErrorCode;
import my.fisherman.fisherman.global.util.MailUtil;
import my.fisherman.fisherman.global.util.ThymeleafUtil;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final Integer AUTH_CODE_LENGTH = 6;
    private final String AUTH_CODE_SUBJECT = "Fisherman 인증 코드";

    private final ThymeleafUtil thymeleafUtil;
    private final MailUtil mailUtil;

    private final AuthenticationRepository authenticationRepository;

    public void sendAuthCode(String email) {
        var authCode = CodeGenerator.generateCode(AUTH_CODE_LENGTH);
        var mailContext = thymeleafUtil.createAuthCodeContext(authCode);

        mailUtil.sendMail(email, mailContext, AUTH_CODE_SUBJECT);

        var authentication = new Authentication(email, authCode);
        authenticationRepository.save(email, authentication);
    }

    public void verifyAuthCode(String email, String authCode) {
        var authentication = authenticationRepository.find(email)
            .orElseThrow(() -> new FishermanException(AuthErrorCode.INVALID_AUTH_REQUEST));

        authentication.verify(authCode);
        authenticationRepository.save(email, authentication);

    }
}
