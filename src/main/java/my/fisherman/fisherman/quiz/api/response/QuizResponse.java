package my.fisherman.fisherman.quiz.api.response;

import java.util.List;

import lombok.Getter;

@Getter
public class QuizResponse {
    private QuizDto quiz;
    private List<QuestionDto> questions;
    
    @Getter
    class QuizDto {
        private Long id;
        private String title;
        private String type;
        private Short wrongCount;
        private Boolean isSolved;
    }

    @Getter
    class QuestionDto {
        private Long id;
        private String content;
        private Boolean isAnswer;
    }
}
