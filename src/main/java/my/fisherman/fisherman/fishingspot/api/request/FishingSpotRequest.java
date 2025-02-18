package my.fisherman.fisherman.fishingspot.api.request;

import java.util.List;
import java.util.Optional;

import my.fisherman.fisherman.fishingspot.application.command.FishingSpotCommand;

public class FishingSpotRequest {

    // TODO: 유효성 검증 추가
    public record Send(
            Long smeltId,
            String title,
            String content,
            String senderName,
            Optional<Quiz> quiz
    ) {
        public FishingSpotCommand.SendSmelt toCommand(Long fishingSpotId) {
            return new FishingSpotCommand.SendSmelt(
                smeltId,
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
    ) {}
}
