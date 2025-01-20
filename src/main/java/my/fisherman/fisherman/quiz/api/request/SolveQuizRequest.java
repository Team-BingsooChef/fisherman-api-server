package my.fisherman.fisherman.quiz.api.request;

public class SolveQuizRequest {
    
    public record Try(
        Long quizId,
        Long questionId
    ) {
    }
}
