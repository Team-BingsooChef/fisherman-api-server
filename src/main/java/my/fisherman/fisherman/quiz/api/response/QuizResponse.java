package my.fisherman.fisherman.quiz.api.response;

import java.util.List;

public class QuizResponse {

    public record Quiz(
        QuizDto quiz,
        List<QuestionDto> questions
    ) {
    }

    public record QuizDto(
        Long id,
        String title,
        String type,
        Short wrongCount,
        Boolean isSolved
    ) {
    }

    public record QuestionDto(
        Long id,
        String content,
        Boolean isAnswer
    ) {
    }
}
