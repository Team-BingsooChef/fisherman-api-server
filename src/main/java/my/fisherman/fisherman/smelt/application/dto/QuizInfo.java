package my.fisherman.fisherman.smelt.application.dto;

import java.util.List;

import my.fisherman.fisherman.smelt.domain.Question;
import my.fisherman.fisherman.smelt.domain.Quiz;

public class QuizInfo {
    public record Detail(
        Simple quiz,
        List<QuestionInfo> questions
    ) {
        public static Detail of(Quiz quiz, List<Question> questions) {
            return new Detail(Simple.from(quiz), questions.stream().map(quest -> QuestionInfo.from(quest, quiz)).toList());
        }
    }

    public record Simple(
        Long id,
        String title,
        String type,
        Short wrongCount,
        Boolean isSolved
    ) {
        public static Simple from(Quiz quiz) {
            return new Simple(
                quiz.getId(), 
                quiz.getTitle(),
                quiz.getType().toString(),
                quiz.getWrongCount(),
                quiz.getIsSolved()
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
