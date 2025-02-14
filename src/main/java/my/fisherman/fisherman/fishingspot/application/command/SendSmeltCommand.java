package my.fisherman.fisherman.fishingspot.application.command;

import java.util.List;

public record SendSmeltCommand(
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
