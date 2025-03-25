package my.fisherman.fisherman.auth.application.dto;

import org.springframework.http.ResponseCookie;

public record Token(
    ResponseCookie accessToken,
    ResponseCookie refreshToken
) {

}
