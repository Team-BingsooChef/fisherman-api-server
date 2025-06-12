package my.fisherman.fisherman.global.util;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import my.fisherman.fisherman.global.exception.AuthErrorCode;
import my.fisherman.fisherman.global.exception.FishermanException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MailUtil {

    private final JavaMailSender mailSender;

    public void sendMail(String email, String context, String subject) {

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            helper.setTo(email);
            helper.setSubject(subject);
            helper.setText(context, true);

            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            throw new FishermanException(AuthErrorCode.EMAIL_REQUEST_FAILED);
        }
    }
}
