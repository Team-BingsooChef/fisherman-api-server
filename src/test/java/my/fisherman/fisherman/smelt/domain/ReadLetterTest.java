package my.fisherman.fisherman.smelt.domain;

import my.fisherman.fisherman.fishingspot.domain.FishingSpot;
import my.fisherman.fisherman.global.exception.FishermanException;
import my.fisherman.fisherman.inventory.domain.Inventory;
import my.fisherman.fisherman.user.domain.User;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.lang.reflect.Field;

import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ReadLetterTest {
    private User sender;
    private User receiver;
    private User other;
    private Inventory senderInventory;
    private FishingSpot receiverFishingSpot;

    // Smelt 생성에 필요한 객체 생성
    @BeforeEach
    void initDependencies() throws NoSuchFieldException, IllegalAccessException {
        Field idField = User.class.getDeclaredField("id");
        idField.setAccessible(true);

        sender = User.of("testSender@gmail.com", "test password", "test sender");
        idField.set(sender, 1L);

        receiver = User.of("testReceiver@gmail.com", "test password", "test receiver");
        idField.set(receiver, 2L);

        other = User.of("testOther@gmail.com", "test password", "test other");
        idField.set(other, 3L);

        senderInventory = Inventory.of(sender);
        receiverFishingSpot = FishingSpot.of(receiver);
    }

    @DisplayName("편지 읽기_받은이 정상 요청")
    @Test
    void readLetterTest_readByReceiver() throws NoSuchFieldException, IllegalAccessException {
        //given
        Smelt smelt = createSmelt(SmeltStatus.SOLVED);

        // when & then
        assertThatNoException().isThrownBy(() -> smelt.readLetter(receiver));
    }

    @DisplayName("편지 읽기_받은이 읽었던 퀴즈")
    @Test
    void readLetterTest_readByReceiver_afterRead() throws NoSuchFieldException, IllegalAccessException {
        //given
        Smelt smelt = createSmelt(SmeltStatus.READ);

        // when & then
        assertThatNoException().isThrownBy(() -> smelt.readLetter(receiver));
    }

    @DisplayName("편지 읽기_보낸이 정상 요청")
    @Test
    void readLetterTest_readBySender() throws NoSuchFieldException, IllegalAccessException {
        //given
        Smelt smelt = createSmelt(SmeltStatus.SOLVED);

        // when & then
        assertThatNoException().isThrownBy(() -> smelt.readLetter(sender));
    }

    @DisplayName("편지 읽기_제 3자 요청")
    @Test
    void readLetterTest_readByOther() throws NoSuchFieldException, IllegalAccessException {
        //given
        Smelt smelt = createSmelt(SmeltStatus.SOLVED);

        // when & then
        assertThatThrownBy(() -> smelt.readLetter(other))
                .isInstanceOf(FishermanException.class);
    }

    @DisplayName("편지 읽기_받은이_아직 풀지 않은 퀴즈")
    @Test
    void readLetterTest_readByReceiver_beforeSolve() throws NoSuchFieldException, IllegalAccessException {
        // given
        Smelt smelt = createSmelt(SmeltStatus.UNREAD);

        // when & then
        assertThatThrownBy(() -> smelt.readLetter(receiver))
                .isInstanceOf(FishermanException.class);
    }

    @DisplayName("편지 읽기_보낸이_아직 풀지 않은 퀴즈")
    @Test
    void readLetterTest_readBySender_beforeSolve() throws NoSuchFieldException, IllegalAccessException {
        // given
        Smelt smelt = createSmelt(SmeltStatus.UNREAD);

        // when & then
        assertThatNoException().isThrownBy(() -> smelt.readLetter(sender));
    }

    @DisplayName("편지 읽기_제 3자_아직 풀지 않은 퀴즈")
    @Test
    void readLetterTest_readByOther_beforeSolve() throws NoSuchFieldException, IllegalAccessException {
        // given
        Smelt smelt = createSmelt(SmeltStatus.UNREAD);

        // when & then
        assertThatThrownBy(() -> smelt.readLetter(other))
                .isInstanceOf(FishermanException.class);
    }

    @DisplayName("편지 읽기_편지 없음")
    @Test
    void readLetterTest_readByOther_beforeSent() throws NoSuchFieldException, IllegalAccessException {
        // given
        Smelt smelt = createSmelt(SmeltStatus.DREW);

        // when & then
        assertThatThrownBy(() -> smelt.readLetter(receiver))
                .isInstanceOf(FishermanException.class);
    }

    /**
     * 주어진 상태에 대해 정상적인 빙어를 생성해 반환한다.
     * 1. 상태가 DREW라면 Inventory만 주입된다.
     * 2. 그 외 상태에서는 Inventory와 FishingSpot, Letter가 주입된다.
     *  - 상태가 UNREAD라면 풀지 않은 퀴즈가 주입된다.
     *  - 상태가 SOLVED나 READ라면 푼 퀴즈가 주입된다.
     */
    // 
    private Smelt createSmelt(SmeltStatus status) throws NoSuchFieldException, IllegalAccessException {
        Smelt smelt = Smelt.of(senderInventory, new SmeltType());

        Field statusField = Smelt.class.getDeclaredField("status");
        statusField.setAccessible(true);
        statusField.set(smelt, status);

        // DREW: Inventory와 Status만 주입한 빙어 반환
        if (status == SmeltStatus.DREW) {
            return smelt;
        }

        Quiz quiz = createQuizBy(status);

        Field fishingSpotField = Smelt.class.getDeclaredField("fishingSpot");
        fishingSpotField.setAccessible(true);
        fishingSpotField.set(smelt, receiverFishingSpot);

        Field letterField = Smelt.class.getDeclaredField("letter");
        letterField.setAccessible(true);
        letterField.set(smelt, Letter.of("test content", "test senderName"));

        Field quizField = Smelt.class.getDeclaredField("quiz");
        quizField.setAccessible(true);
        quizField.set(smelt, quiz);

        return smelt;
    }

    // 빙어의 상태에 정상적인 퀴즈를 생성한다.
    static Quiz createQuizBy(SmeltStatus status) throws NoSuchFieldException, IllegalAccessException {
        Quiz quiz = Quiz.of("test quiz", QuizType.OX);

        Field isSolvedField = Quiz.class.getDeclaredField("isSolved");
        isSolvedField.setAccessible(true);
        if (status == SmeltStatus.UNREAD) {
            isSolvedField.set(quiz, false);
        } else {
            isSolvedField.set(quiz, true);
        }

        return quiz;
    }
}
