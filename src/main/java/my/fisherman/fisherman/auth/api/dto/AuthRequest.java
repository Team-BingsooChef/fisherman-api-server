package my.fisherman.fisherman.auth.api.dto;

import jakarta.validation.constraints.Email;

public class AuthRequest {

    public record Mail(
        @Email(message = "이메일 형식이 올바르지 않습니다.")
        String email
    ) {

    }
}
