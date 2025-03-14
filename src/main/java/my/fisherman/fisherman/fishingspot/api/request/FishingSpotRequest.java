package my.fisherman.fisherman.fishingspot.api.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.util.List;
import java.util.Optional;
import my.fisherman.fisherman.fishingspot.application.command.FishingSpotCommand;
import org.hibernate.validator.constraints.Length;

public class FishingSpotRequest {

    public record Send(
        @NotNull(message = "빙어 종류 ID는 필수입니다.")
        @Positive(message = "빙어 종류 ID는 양수여야 합니다.")
        Long smeltTypeId,
        @NotBlank(message = "편지 제목은 필수입니다.")
        @Length(max = 15, message = "편지의 제목은 15자 이하여야 합니다.")
        String title,
        @NotBlank(message = "편지 내용은 필수입니다.")
        @Length(max = 300, message = "편지의 내용은 300자 이하여야 합니다.")
        String content,
        @NotBlank(message = "보낸이는 필수입니다.")
        @Length(max = 300, message = "보낸이는 8자 이하여야 합니다.")
        String senderName,
        Optional<Quiz> quiz
    ) {

        public FishingSpotCommand.SendSmelt toCommand(Long fishingSpotId) {
            return new FishingSpotCommand.SendSmelt(
                smeltTypeId,
                fishingSpotId,
                senderName,
                title,
                content,
                quiz.isPresent(),
                quiz.map(Quiz::title).orElse(null),
                quiz.map(Quiz::content).orElse(null),
                quiz.map(Quiz::type).orElse(null),
                quiz.map(Quiz::questions).orElse(null),
                quiz.map(Quiz::answerIndex).orElse(null)
            );
        }
    }

    record Quiz(
        String title,
        String content,
        String type,
        List<String> questions,
        int answerIndex
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
