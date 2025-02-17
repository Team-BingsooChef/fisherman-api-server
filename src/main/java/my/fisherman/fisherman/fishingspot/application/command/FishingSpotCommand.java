package my.fisherman.fisherman.fishingspot.application.command;

import java.util.List;

public class FishingSpotCommand{
    
    public record SendSmelt(
        Long smeltId,
        Long fishingSpotId,
        String senderName,    
        String letterTitle,
        String letterContent,
        Boolean existQuiz,
        String quizTitle,
        String quizContent,
        String quiztype,
        List<String> questions,
        int answerIndex
    ) {
    }
}
