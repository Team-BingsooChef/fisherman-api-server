package my.fisherman.fisherman.quiz.api.request;

import lombok.Getter;

@Getter
public class SolveQuizRequest {
    Long quizId;
    Long questionId;
}
