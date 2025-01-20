package my.fisherman.fisherman.quiz.api.response;

public class SolveQuizResponse {
    
    public record Result(
        Boolean result,
        Short wrongCount
    ) {
    }
}
