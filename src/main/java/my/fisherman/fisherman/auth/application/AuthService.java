package my.fisherman.fisherman.auth.application;

import lombok.RequiredArgsConstructor;
import my.fisherman.fisherman.auth.application.util.CodeGenerator;
import my.fisherman.fisherman.global.util.MailUtil;
import my.fisherman.fisherman.global.util.ThymeleafUtil;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private static final Integer AUTH_CODE_LENGTH = 6;
    private static final String AUTH_CODE_SUBJECT = "Fisherman 인증 코드";

    private final ThymeleafUtil thymeleafUtil;
    private final MailUtil mailUtil;

    public void sendAuthCode(String email) {
        var authCode = CodeGenerator.generateCode(AUTH_CODE_LENGTH);
        var mailContext = thymeleafUtil.createAuthCodeContext(authCode);

        mailUtil.sendMail(email, mailContext, AUTH_CODE_SUBJECT);
    }
}
