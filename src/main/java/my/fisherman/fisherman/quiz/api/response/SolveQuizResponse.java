package my.fisherman.fisherman.quiz.api.response;

import lombok.Getter;

@Getter
public class SolveQuizResponse {
    private Boolean result;
    private Short wrongCount;
}
