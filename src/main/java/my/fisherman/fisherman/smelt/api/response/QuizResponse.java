package my.fisherman.fisherman.smelt.api.response;

import my.fisherman.fisherman.smelt.application.dto.QuizInfo;

import java.util.List;

public class QuizResponse {
    
    public record Detail(
            Quiz quiz,
            List<Question> questions
    ) {
        public static Detail from(QuizInfo.Detail info) {
                return new Detail(Quiz.from(info), info.questions().stream().map(Question::from).toList());
        }
    }
    
    public record SolveResult(
        Boolean result,
        Short wrongCount
    ) {
    }

    record Quiz(
            Long id,
            String title,
            String type,
            Short wrongCount,
            Boolean isSolved
    ) {
        static Quiz from(QuizInfo.Detail info) {
                return new Quiz(info.id(), info.title(), info.type(), info.wrongCount(), info.isSolved());
        }
    }

    record Question(
            Long id,
            String content,
            Boolean isAnswer
    ) {
        static Question from(QuizInfo.QuestionInfo info) {
                return new Question(info.id(), info.content(), info.isAnswer());
        }
    }
}
