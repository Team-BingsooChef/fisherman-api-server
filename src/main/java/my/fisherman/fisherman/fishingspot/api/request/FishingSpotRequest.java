package my.fisherman.fisherman.fishingspot.api.request;

import java.util.List;

import my.fisherman.fisherman.fishingspot.application.command.SendSmeltCommand;

public class FishingSpotRequest {

    // TODO: 유효성 검증 추가
    public record Send(
            Long smeltId,
            String title,
            String content,
            String senderName,
            Quiz quiz
    ) {
        public SendSmeltCommand toCommand(Long fishingSpotId) {
            return new SendSmeltCommand(
                fishingSpotId,
                smeltId,
                senderName,
                title,
                content,
                true,
                quiz.title,
                quiz.content,
                quiz.type,
                quiz.questions,
                quiz.answerIndex);
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
