package my.fisherman.fisherman.smelt.api.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class QuizRequest {
    
    public record Solve(
        @NotNull(message = "선지 ID는 필수입니다.")
        @Positive(message = "선지 ID는 양수여야 합니다.")
        Long questionId
    ) {
    }
}
