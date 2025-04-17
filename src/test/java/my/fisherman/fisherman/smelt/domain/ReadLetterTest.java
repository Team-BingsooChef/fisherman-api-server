package my.fisherman.fisherman.smelt.domain;

import my.fisherman.fisherman.fishingspot.domain.FishingSpot;
import my.fisherman.fisherman.global.exception.FishermanException;
import my.fisherman.fisherman.inventory.domain.Inventory;
import my.fisherman.fisherman.user.domain.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ReadLetterTest {
    private static User sender;
    private static User receiver;
    private static User other;
    private static Inventory senderInventory;
    private static FishingSpot receiverFishingSpot;

    // 정상 요청 상태로 초기화
    @BeforeAll
    static void init() throws NoSuchFieldException, IllegalAccessException {
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
        Smelt smelt = createSmeltBeforeSent();

        // when & then
        assertThatThrownBy(() -> smelt.readLetter(receiver))
                .isInstanceOf(FishermanException.class);
    }

    private Smelt createSmelt(SmeltStatus status) throws NoSuchFieldException, IllegalAccessException {
        Quiz quiz = Quiz.of("test quiz", QuizType.OX);
        Field isSolvedField = Quiz.class.getDeclaredField("isSolved");
        isSolvedField.setAccessible(true);
        if (status == SmeltStatus.SOLVED || status == SmeltStatus.READ) {
            isSolvedField.set(quiz, true);
        } else {
            isSolvedField.set(quiz, false);
        }

        Smelt smelt = Smelt.of(senderInventory, new SmeltType());

        Field fishingSpotField = Smelt.class.getDeclaredField("fishingSpot");
        fishingSpotField.setAccessible(true);
        fishingSpotField.set(smelt, receiverFishingSpot);

        Field letterField = Smelt.class.getDeclaredField("letter");
        letterField.setAccessible(true);
        letterField.set(smelt, Letter.of("test content", "test senderName"));

        Field statusField = Smelt.class.getDeclaredField("status");
        statusField.setAccessible(true);
        statusField.set(smelt, status);

        Field quizField = Smelt.class.getDeclaredField("quiz");
        quizField.setAccessible(true);
        quizField.set(smelt, quiz);

        return smelt;
    }

    private Smelt createSmeltBeforeSent() throws NoSuchFieldException, IllegalAccessException {
        Smelt smelt = Smelt.of(senderInventory, new SmeltType());

        Field statusField = Smelt.class.getDeclaredField("status");
        statusField.setAccessible(true);
        statusField.set(smelt, SmeltStatus.DREW);

        return smelt;
    }
}
