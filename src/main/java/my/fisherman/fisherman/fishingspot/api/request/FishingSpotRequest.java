package my.fisherman.fisherman.fishingspot.api.request;

import java.util.List;

import my.fisherman.fisherman.fishingspot.application.command.FishingSpotCommand;

public class FishingSpotRequest {

    // TODO: 유효성 검증 추가
    public record Send(
            Long smeltId,
            String title,
            String content,
            String senderName,
            Quiz quiz
    ) {
        public FishingSpotCommand.SendSmelt toCommand(Long fishingSpotId) {
            return new FishingSpotCommand.SendSmelt(
                fishingSpotId,
                smeltId,
                senderName,
                title,
                content,
                quiz != null,
                quiz != null ? quiz.title : null,
                quiz != null ? quiz.content : null,
                quiz != null ? quiz.type : null,
                quiz != null ? quiz.questions : null,
                quiz != null ? quiz.answerIndex : null);
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
