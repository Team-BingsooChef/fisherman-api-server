package my.fisherman.fisherman.smelt.api.response;

import my.fisherman.fisherman.smelt.domain.QuizType;

import java.util.List;

public class QuizResponse {
    
    public record Info(
            Quiz quiz,
            List<Question> questions
    ) {
    }
    
    public record SolveResult(
        Boolean result,
        Short wrongCount
    ) {
    }

    record Quiz(
            Long id,
            String title,
            QuizType type,
            Short wrongCount,
            Boolean isSolved
    ) {
    }

    record Question(
            Long id,
            String content,
            Boolean isAnswer
    ) {
    }
}
