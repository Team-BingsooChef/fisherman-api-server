package my.fisherman.fisherman.smelt.api.request;

public class QuizRequest {
    
    public record Solve(
        Long quizId,
        Long questionId
    ) {
    }
}
