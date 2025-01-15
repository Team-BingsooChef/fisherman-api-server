package my.fisherman.fisherman.auth.api.dto;

import jakarta.validation.constraints.Email;

public class AuthRequest {

    public record Mail(
            @Email
            String email
    ) {
    }
}
