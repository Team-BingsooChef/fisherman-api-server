package my.fisherman.fisherman.smelt.domain;

import my.fisherman.fisherman.global.exception.FishermanException;
import my.fisherman.fisherman.global.exception.code.SmeltErrorCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("퀴즈 테스트")
public class QuizTest {

    @DisplayName("아직 퀴즈를 풀지 않았을 때")
    @Nested
    class NotSolvedQuiz {
        private Quiz quiz;

        @BeforeEach
        void initialize() throws NoSuchFieldException, IllegalAccessException {
            quiz =  createQuizWith(1L, false);
        }

        @Test
        @DisplayName("정답이 제출되면 상태가 변경된다")
        void solve_tryWithAnswerQuestion() {
            short prevWrongCount = quiz.getWrongCount();
            Question answerQuestion = Question.of("content", true, quiz);

            quiz.trySolve(answerQuestion);

            assertThat(quiz.getIsSolved()).isEqualTo(true);
            assertThat(quiz.getWrongCount()).isEqualTo(prevWrongCount);
        }

        @Test
        @DisplayName("오답이 제출되면 틀린 횟수가 증가한다")
        void increaseWrongCount_tryWithWrongQuestion() {
            short prevWrongCount = quiz.getWrongCount();
            Question wrongQuestion = Question.of("content", false, quiz);

            quiz.trySolve(wrongQuestion);

            assertThat(quiz.getIsSolved()).isEqualTo(false);
            assertThat(quiz.getWrongCount()).isEqualTo(++prevWrongCount);
        }

        @Test
        @DisplayName("다른 퀴즈의 답이 제출되면 실패한다")
        void fail_tryWithOtherQuestion() throws NoSuchFieldException, IllegalAccessException {
            short prevWrongCount = quiz.getWrongCount();
            boolean prevIsSolved = quiz.getIsSolved();
            Quiz otherQuiz = createQuizWith(quiz.getId() + 1, true);
            Question otherQuestion = Question.of("content", false, otherQuiz);

            // when & then: 풀이 실패
            assertThatThrownBy(() -> quiz.trySolve(otherQuestion))
                    .isInstanceOf(FishermanException.class)
                    .extracting("errorCode")
                    .isEqualTo(SmeltErrorCode.BAD_QUESTION);

            // then: 상태 유지
            assertThat(quiz.getIsSolved()).isEqualTo(prevIsSolved);
            assertThat(quiz.getWrongCount()).isEqualTo(prevWrongCount);
        }
    }

    @DisplayName("이미 퀴즈를 풀었을 때")
    @Nested
    class SolvedQuiz {
        private Quiz quiz;

        @BeforeEach
        void initialize() throws NoSuchFieldException, IllegalAccessException {
            quiz =  createQuizWith(1L, true);
        }

        @Test
        @DisplayName("정답이 제출되면 실패한다")
        void fail_tryWithAnswerQuestion() {
            short prevWrongCount = quiz.getWrongCount();
            boolean prevIsSolved = quiz.getIsSolved();
            Question answerQuestion = Question.of("content", true, quiz);

            // when & then: 풀이 실패
            assertThatThrownBy(() -> quiz.trySolve(answerQuestion))
                    .isInstanceOf(FishermanException.class)
                    .extracting("errorCode")
                    .isEqualTo(SmeltErrorCode.ALREADY_SOLVED);

            // then: 상태 유지
            assertThat(quiz.getIsSolved()).isEqualTo(prevIsSolved);
            assertThat(quiz.getWrongCount()).isEqualTo(prevWrongCount);
        }

        @Test
        @DisplayName("오답이 제출되면 실패한다")
        void fail_tryWithWrongQuestion() {
            short prevWrongCount = quiz.getWrongCount();
            boolean prevIsSolved = quiz.getIsSolved();
            Question wrongQuestion = Question.of("content", false, quiz);

            // when & then: 풀이 실패
            assertThatThrownBy(() -> quiz.trySolve(wrongQuestion))
                    .isInstanceOf(FishermanException.class)
                    .extracting("errorCode")
                    .isEqualTo(SmeltErrorCode.ALREADY_SOLVED);

            // then: 상태 유지
            assertThat(quiz.getIsSolved()).isEqualTo(prevIsSolved);
            assertThat(quiz.getWrongCount()).isEqualTo(prevWrongCount);
        }

        @Test
        @DisplayName("다른 퀴즈의 답이 제출되면 실패한다")
        void fail_tryWithOtherQuestion() throws NoSuchFieldException, IllegalAccessException {
            short prevWrongCount = quiz.getWrongCount();
            boolean prevIsSolved = quiz.getIsSolved();
            Quiz otherQuiz = createQuizWith(quiz.getId() + 1L, true);
            Question otherQuestion = Question.of("content", false, otherQuiz);

            // when & then: 풀이 실패
            assertThatThrownBy(() -> quiz.trySolve(otherQuestion))
                    .isInstanceOf(FishermanException.class)
                    .extracting("errorCode")
                    .isEqualTo(SmeltErrorCode.BAD_QUESTION);

            // then: 상태 유지
            assertThat(quiz.getIsSolved()).isEqualTo(prevIsSolved);
            assertThat(quiz.getWrongCount()).isEqualTo(prevWrongCount);
        }
    }

    private Quiz createQuizWith(Long id, boolean isSolved) throws NoSuchFieldException, IllegalAccessException {
        Quiz quiz = Quiz.of("quiz title", QuizType.OX);

        Field idField = Quiz.class.getDeclaredField("id");
        idField.setAccessible(true);
        idField.set(quiz, id);

        Field isSolvedField = Quiz.class.getDeclaredField("isSolved");
        isSolvedField.setAccessible(true);
        isSolvedField.set(quiz, isSolved);

        return quiz;
    }
}
