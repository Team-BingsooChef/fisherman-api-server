package my.fisherman.fisherman.smelt.application.dto;

import java.util.List;

import my.fisherman.fisherman.smelt.domain.Question;
import my.fisherman.fisherman.smelt.domain.Quiz;

public class QuizInfo {

    public record Detail(
        Long id,
        String title,
        String type,
        Short wrongCount,
        Boolean isSolved,
        List<QuestionInfo> questions
    ) {
        public static Detail of(Quiz quiz, List<Question> questions) {
            return new Detail(
                quiz.getId(), 
                quiz.getTitle(),
                quiz.getType().toString(),
                quiz.getWrongCount(),
                quiz.getIsSolved(),
                questions.stream().map(q -> QuestionInfo.from(q, quiz)).toList()
            );
        }
    }

    public record QuestionInfo(
        Long id,
        String content,
        Boolean isAnswer
    ) {
        public static QuestionInfo from(Question question, Quiz quiz) {
            return new QuestionInfo(question.getId(), question.getContent(), quiz.getIsSolved() ? question.getIsAnswer() : null);
        }
    }
}
