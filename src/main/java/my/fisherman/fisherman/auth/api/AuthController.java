package my.fisherman.fisherman.auth.api;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import my.fisherman.fisherman.auth.api.dto.AuthRequest;
import my.fisherman.fisherman.auth.application.AuthService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController implements AuthSpecification {
    private final AuthService authService;

    @Override
    @PostMapping("/email")
    public void sendAuthCode(@RequestBody @Valid AuthRequest.Mail request) {
        authService.sendAuthCode(request.email());
    }

    @Override
    @PostMapping("/email/verify")
    public void verifyAuthCode(
            @RequestBody @Valid AuthRequest.Mail request,
            @RequestParam("auth_code") String authCode
    ) {
        authService.verifyAuthCode(request.email(), authCode);
    }
}
