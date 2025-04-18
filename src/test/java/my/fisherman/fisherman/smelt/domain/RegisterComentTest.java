package my.fisherman.fisherman.smelt.domain;

import my.fisherman.fisherman.fishingspot.domain.FishingSpot;
import my.fisherman.fisherman.global.exception.FishermanException;
import my.fisherman.fisherman.inventory.domain.Inventory;
import my.fisherman.fisherman.user.domain.User;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class RegisterComentTest {
    private User sender;
    private User receiver;
    private User other;
    private Inventory senderInventory;
    private FishingSpot receiverFishingSpot;
    private Smelt smelt;

    /**
     * 빙어와 테스트에 사용할 인스턴스를 정상 상태로 생성한다.
     * 1. Inventory와 FishingSpot, Letter 주입
     * 2. Smelt의 상태는 READ
     */
    @BeforeEach
    void intialize() throws NoSuchFieldException, IllegalAccessException {
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

        smelt = Smelt.of(senderInventory, new SmeltType());
        Field fishingSpotField = Smelt.class.getDeclaredField("fishingSpot");
        fishingSpotField.setAccessible(true);
        fishingSpotField.set(smelt, receiverFishingSpot);
        Field letterField = Smelt.class.getDeclaredField("letter");
        letterField.setAccessible(true);
        letterField.set(smelt, Letter.of("test content", "test senderName"));
        Field statusField = Smelt.class.getDeclaredField("status");
        statusField.setAccessible(true);
        statusField.set(smelt, SmeltStatus.READ);
    }

    @DisplayName("답변 등록")
    @Test
    void registerCommentTest() {
        Comment comment = Comment.of("test content");

        assertThatNoException().isThrownBy(() -> smelt.registerComment(receiver, comment));
    }

    @DisplayName("답변 등록_코멘트 누락")
    @Test
    void registerCommentTest_registerNull() {
        Comment comment = null;

        assertThatNoException().isThrownBy(() -> smelt.registerComment(receiver, comment));
    }

    @DisplayName("답변 등록_이미 답변한 빙어")
    @Test
    void registerCommentTest_alreadyRegister() throws NoSuchFieldException, IllegalAccessException {
        // given
        Letter letter = Letter.of("test content", "test senderName");
        Field commentField = Letter.class.getDeclaredField("comment");
        commentField.setAccessible(true);
        commentField.set(letter, Comment.of("test content 1"));

        Field letterField = Smelt.class.getDeclaredField("letter");
        letterField.setAccessible(true);
        letterField.set(smelt, letter);

        Comment comment = Comment.of("test content 2");

        // when & then
        assertThatThrownBy(() -> smelt.registerComment(receiver, comment))
                .isInstanceOf(FishermanException.class);
    }

    @DisplayName("답변 등록_자기가 받지 않은 빙어")
    @Test
    void registerCommentTest_notMine() {
        Comment comment = Comment.of("test content");

        assertThatThrownBy(() -> smelt.registerComment(other, comment))
                .isInstanceOf(FishermanException.class);
    }

    @DisplayName("답변 등록_자기가 보낸 빙어")
    @Test
    void registerCommentTest_sentByMe() {
        Comment comment = Comment.of("test content");

        assertThatThrownBy(() -> smelt.registerComment(sender, comment))
                .isInstanceOf(FishermanException.class);
    }

    @DisplayName("답변 등록_아직 읽지 않은 빙어")
    @Test
    void registerCommentTest_beforeRead() throws NoSuchFieldException, IllegalAccessException {
        // given
        Field statusField = Smelt.class.getDeclaredField("status");
        statusField.setAccessible(true);
        statusField.set(smelt, SmeltStatus.UNREAD);
        Comment comment = Comment.of("test content");

        // when & then
        assertThatThrownBy(() -> smelt.registerComment(receiver, comment))
                .isInstanceOf(FishermanException.class);
    }

    @DisplayName("답변 등록_아직 보내지 않은 빙어")
    @Test
    void registerCommentTest_beforeSend() throws NoSuchFieldException, IllegalAccessException {
        // given
        Smelt smelt = Smelt.of(senderInventory, new SmeltType());

        Comment comment = Comment.of("test content");

        // when & then
        assertThatThrownBy(() -> smelt.registerComment(receiver, comment))
                .isInstanceOf(FishermanException.class);
    }
}
