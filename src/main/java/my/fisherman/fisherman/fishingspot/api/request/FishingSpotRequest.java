package my.fisherman.fisherman.fishingspot.api.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.util.List;
import java.util.Optional;

import jakarta.validation.constraints.Size;
import my.fisherman.fisherman.fishingspot.application.command.FishingSpotCommand;
import org.hibernate.validator.constraints.Length;

public class FishingSpotRequest {

    public record Send(
        @NotNull(message = "빙어 종류 ID는 필수입니다.")
        @Positive(message = "빙어 종류 ID는 양수여야 합니다.")
        Long smeltTypeId,
        @NotBlank(message = "편지 내용은 필수입니다.")
        @Length(max = 300, message = "편지의 내용은 300자 이하여야 합니다.")
        String content,
        @NotBlank(message = "보낸이는 필수입니다.")
        @Length(max = 300, message = "보낸이는 8자 이하여야 합니다.")
        String senderName,
        @Valid
        Optional<Quiz> quiz
    ) {

        public FishingSpotCommand.SendSmelt toCommand(Long fishingSpotId) {
            return new FishingSpotCommand.SendSmelt(
                smeltTypeId,
                fishingSpotId,
                senderName,
                content,
                quiz.isPresent(),
                quiz.map(Quiz::title).orElse(null),
                quiz.map(Quiz::type).orElse(null),
                quiz.map(Quiz::questions).orElse(null),
                quiz.map(Quiz::answerIndex).orElse(null)
            );
        }
    }

    record Quiz(
        @Length(max=30, message = "퀴즈의 질문은 30자 이하여야 합니다.")
        String title,
        String type,
        @Size(min=2, max=4, message = "퀴즈의 선지는 2~4개여야 합니다.")
        List<String> questions,
        @NotNull(message = "정답 인덱스는 필수입니다.")
        Integer answerIndex
    ) {
    }


    public record UpdatePublic(
        boolean isPublic
    ) {

        public FishingSpotCommand.UpdatePublic toCommand(Long fishingSpotId) {
            return new FishingSpotCommand.UpdatePublic(fishingSpotId, isPublic);

        }
    }
}
