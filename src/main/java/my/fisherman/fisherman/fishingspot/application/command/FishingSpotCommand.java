package my.fisherman.fisherman.fishingspot.application.command;

import java.util.List;

public class FishingSpotCommand {

    public record SendSmelt(
        Long smeltTypeId,
        Long fishingSpotId,
        String senderName,
        String letterContent,
        Boolean existQuiz,
        String quizTitle,
        String quiztype,
        List<String> questions,
        int answerIndex
    ) {

    }

    public record UpdatePublic(
        Long fishingSpotId,
        Boolean isPublic
    ) {

    }
}
