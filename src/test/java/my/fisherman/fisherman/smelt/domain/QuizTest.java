package my.fisherman.fisherman.smelt.domain;

import my.fisherman.fisherman.global.exception.FishermanException;
import my.fisherman.fisherman.global.exception.code.SmeltErrorCode;
import my.fisherman.fisherman.util.TestFixtureUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("퀴즈 테스트")
public class QuizTest {

    @DisplayName("아직 퀴즈를 풀지 않았을 때")
    @Nested
    class NotSolvedQuiz {
        private Quiz quiz;

        @BeforeEach
        void initialize() {
            quiz =  TestFixtureUtil.createQuizWith(1L, false);
        }

        @Test
        @DisplayName("정답이 제출되면 상태가 변경된다")
        void solve_tryWithAnswerQuestion() {
            Question answerQuestion = Question.of("content", true, quiz);
            short prevWrongCount = quiz.getWrongCount();

            quiz.trySolve(answerQuestion);

            assertThat(quiz.getIsSolved()).isEqualTo(true);
            assertThat(quiz.getWrongCount()).isEqualTo(prevWrongCount);
        }

        @Test
        @DisplayName("오답이 제출되면 틀린 횟수가 증가한다")
        void increaseWrongCount_tryWithWrongQuestion() {
            Question wrongQuestion = TestFixtureUtil.createQuestionWith("content", false, quiz);
            short prevWrongCount = quiz.getWrongCount();

            quiz.trySolve(wrongQuestion);

            assertThat(quiz.getIsSolved()).isEqualTo(false);
            assertThat(quiz.getWrongCount()).isEqualTo(++prevWrongCount);
        }

        @Test
        @DisplayName("다른 퀴즈의 선지가 제출되면 실패한다")
        void fail_tryWithOtherQuestion() {
            Quiz otherQuiz = TestFixtureUtil.createQuizWith(quiz.getId() + 1, true);
            Question otherQuestion = TestFixtureUtil.createQuestionWith("content", true, otherQuiz);
            boolean prevIsSolved = quiz.getIsSolved();
            short prevWrongCount = quiz.getWrongCount();

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
        void initialize() {
            quiz =  TestFixtureUtil.createQuizWith(1L, true);
        }

        @Test
        @DisplayName("정답이 제출되면 실패한다")
        void fail_tryWithAnswerQuestion() {
            Question answerQuestion =  TestFixtureUtil.createQuestionWith("content", true, quiz);
            boolean prevIsSolved = quiz.getIsSolved();
            short prevWrongCount = quiz.getWrongCount();

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
            Question wrongQuestion = TestFixtureUtil.createQuestionWith("content", false, quiz);
            boolean prevIsSolved = quiz.getIsSolved();
            short prevWrongCount = quiz.getWrongCount();

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
        void fail_tryWithOtherQuestion() {
            Quiz otherQuiz = TestFixtureUtil.createQuizWith(quiz.getId() + 1L, true);
            Question otherQuestion = TestFixtureUtil.createQuestionWith("content", true, otherQuiz);
            boolean prevIsSolved = quiz.getIsSolved();
            short prevWrongCount = quiz.getWrongCount();

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
}
