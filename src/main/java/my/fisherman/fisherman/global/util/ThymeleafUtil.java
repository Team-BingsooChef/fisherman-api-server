package my.fisherman.fisherman.global.util;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

@Component
@RequiredArgsConstructor
public class ThymeleafUtil {
    private final String AUTH_CODE_TEMPLATE = "auth-code";
    private final String AUTH_CODE_KEY = "authCode";
    private final SpringTemplateEngine templateEngine;

    public String createAuthCodeContext(List<Integer> authCode) {
        var context = new Context();
        var textAuthCode = authCode.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(""));

        context.setVariable(AUTH_CODE_KEY, textAuthCode);
        return templateEngine.process(AUTH_CODE_TEMPLATE, context);
    }
}
