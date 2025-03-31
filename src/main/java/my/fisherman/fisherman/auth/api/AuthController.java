package my.fisherman.fisherman.auth.api;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import my.fisherman.fisherman.auth.api.dto.AuthRequest;
import my.fisherman.fisherman.auth.application.AuthService;
import my.fisherman.fisherman.auth.application.dto.Token;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
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

    @Override
    @PostMapping("/refresh")
    public ResponseEntity<Void> refreshToken(
        @CookieValue("refresh_token") String refreshToken
    ) {
        Token cookie = authService.refreshToken(refreshToken);

        return ResponseEntity.ok()
            .header(HttpHeaders.SET_COOKIE, cookie.accessToken().toString())
            .header(HttpHeaders.SET_COOKIE, cookie.refreshToken().toString())
            .build();
    }

    @Override
    @PostMapping("/withdraw")
    public ResponseEntity<Void> withdraw(
        @CookieValue("refresh_token") String refreshToken
    ) {
        authService.withdraw(refreshToken);
        return ResponseEntity.ok().build();
    }

    @Override
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(
        @CookieValue("refresh_token") String refreshToken
    ) {
        Token cookie = authService.logout(refreshToken);

        return ResponseEntity.ok()
            .header(HttpHeaders.SET_COOKIE, cookie.accessToken().toString())
            .header(HttpHeaders.SET_COOKIE, cookie.refreshToken().toString())
            .build();
    }

}
