package my.fisherman.fisherman.smelt.api.request;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotBlank;

public class SmeltRequest {

    public record RegisterComment(
        @NotBlank(message = "댓글은 필수입니다.")
        @Length(max = 20, message = "댓글은 20자 이하여야 합니다.")
        String content
    ) {
    }
}
