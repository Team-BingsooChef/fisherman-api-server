package my.fisherman.fisherman.smelt.domain;

import my.fisherman.fisherman.fishingspot.domain.FishingSpot;
import my.fisherman.fisherman.global.exception.FishermanException;
import my.fisherman.fisherman.global.exception.code.SmeltErrorCode;
import my.fisherman.fisherman.inventory.domain.Inventory;
import my.fisherman.fisherman.user.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.lang.reflect.Field;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("빙어 도메인 테스트")
public class SmeltTest {
    private User sender;
    private User receiver;
    private User other;
    private SmeltType smeltType;

    @BeforeEach
    void initializeUser() throws NoSuchFieldException, IllegalAccessException {
        sender = createUserWith(1L, "test@gmail.com", "test password", "test sender");
        receiver = createUserWith(2L, "test@gmail.com", "test password", "test sender");
        other = createUserWith(3L, "test@gmail.com", "test password", "test sender");

        smeltType = createSmeltTypeMockId();
    }

    @DisplayName("빙어 보내기 테스트")
    @Nested
    class SendSmeltTest {
        private Letter letter;

        @BeforeEach
        void initializeLetter() throws NoSuchFieldException, IllegalAccessException {
            letter = createLetterWith(1L, "test sender");
        }

        @Test
        @DisplayName("퀴즈 없이 보낼 수 있다")
        void succeed_withoutQuiz() throws NoSuchFieldException, IllegalAccessException {
            Inventory inventory = Inventory.of(sender);
            FishingSpot fishingSpot = FishingSpot.of(receiver);
            Smelt smelt = createSmeltWith(inventory, smeltType);

            smelt.send(inventory, fishingSpot, letter, null);

            assertThat(smelt.getStatus()).isEqualTo(SmeltStatus.UNREAD);
            assertThat(smelt.getLetter()).isEqualTo(letter);
        }

        @Test
        @DisplayName("퀴즈와 보낼 수 있다")
        void succeed_withQuiz() throws NoSuchFieldException, IllegalAccessException {
            Inventory inventory = Inventory.of(sender);
            FishingSpot fishingSpot = FishingSpot.of(receiver);
            Smelt smelt = createSmeltWith(inventory, smeltType);
            Quiz quiz = Quiz.of("title", QuizType.OX);

            smelt.send(inventory, fishingSpot, letter, quiz);

            assertThat(smelt.getStatus()).isEqualTo(SmeltStatus.UNREAD);
            assertThat(smelt.getLetter()).isEqualTo(letter);
            assertThat(smelt.getQuiz()).isEqualTo(quiz);
        }

        @Test
        @DisplayName("내 낚시터에 보낼 수 없다")
        void fail_toMyFishingSpot() throws NoSuchFieldException, IllegalAccessException {
            Inventory inventory = Inventory.of(sender);
            FishingSpot fishingSpot = FishingSpot.of(sender);
            Smelt smelt = createSmeltWith(inventory, smeltType);

            assertThatThrownBy(() -> smelt.send(inventory, fishingSpot, letter, null))
                    .isInstanceOf(FishermanException.class)
                    .extracting("errorCode")
                    .isEqualTo(SmeltErrorCode.NOT_MINE);
        }

        @Test
        @DisplayName("타인의 빙어를 보낼 수 없다")
        void fail_smeltOfOther() throws NoSuchFieldException, IllegalAccessException {
            Inventory otherInventory = Inventory.of(other);
            FishingSpot fishingSpot = FishingSpot.of(receiver);
            Smelt smelt = createSmeltWith(otherInventory, smeltType);
            Inventory senderInventory = Inventory.of(sender);

            assertThatThrownBy(() -> smelt.send(senderInventory, fishingSpot, letter, null))
                    .isInstanceOf(FishermanException.class)
                    .extracting("errorCode")
                    .isEqualTo(SmeltErrorCode.FORBIDDEN);
        }

        @Test
        @DisplayName("이미 보냈던 빙어는 다시 보낼 수 없다")
        void fail_alreadySentSmelt() throws IllegalAccessException, NoSuchFieldException {
            Inventory inventory = Inventory.of(sender);
            FishingSpot fishingSpot = FishingSpot.of(receiver);
            Letter prevLetter = createLetterWith(2L, "Test letter");
            Smelt smelt = createSmeltWith(inventory, smeltType, fishingSpot, prevLetter, SmeltStatus.UNREAD);

            assertThatThrownBy(() -> smelt.send(inventory, fishingSpot, letter, null))
                    .isInstanceOf(FishermanException.class)
                    .extracting("errorCode")
                    .isEqualTo(SmeltErrorCode.ALREADY_SENT);
        }
    }

    @DisplayName("편지 읽기 테스트")
    @Nested
    class ReadLetterTest {
        private Inventory senderInventory;
        private FishingSpot receiverFishingSpot;

        @BeforeEach
        void initialize() {
            senderInventory = Inventory.of(sender);
            receiverFishingSpot = FishingSpot.of(receiver);
        }

        @DisplayName("아직 받은 사람이 읽지 않은 퀴즈 없는 편지일 때")
        @Nested
        class FirstReadLetter {
            private Smelt smelt;
            private SmeltStatus prevStatus;

            @BeforeEach
            void initializeSmelt() throws NoSuchFieldException, IllegalAccessException {
                Letter letter = createLetterWith(1L, "sender");
                prevStatus = SmeltStatus.UNREAD;
                smelt = createSmeltWith(senderInventory, smeltType, receiverFishingSpot, letter, prevStatus);
            }

            @DisplayName("받은 사람이 읽으면 상태가 READ로 바뀐다")
            @Test
            void shouldChangeStatus_readByReceiver() {
                smelt.readLetter(receiver);

                assertThat(smelt.getStatus()).isEqualTo(SmeltStatus.READ);
            }

            @DisplayName("보낸 사람이 읽으면 상태가 바뀌지 않는다")
            @Test
            void shouldNotChangeStatus_readBySender() {
                smelt.readLetter(sender);

                assertThat(smelt.getStatus()).isEqualTo(prevStatus);
            }

            @DisplayName("제 3자가 읽을 수 없다")
            @Test
            void fail_readByOther() {
                assertThatThrownBy(() -> smelt.readLetter(other))
                        .isInstanceOf(FishermanException.class)
                        .extracting("errorCode")
                        .isEqualTo(SmeltErrorCode.FORBIDDEN);
            }
        }

        @DisplayName("아직 받은 사람이 읽지 않은 퀴즈를 푼 편지일 때")
        @Nested
        class FirstReadLetterWithQuiz {
            private Smelt smelt;
            private SmeltStatus prevStatus;

            @BeforeEach
            void initializeSmelt() throws NoSuchFieldException, IllegalAccessException {
                Letter letter = createLetterWith(1L, "sender");
                Quiz quiz = createQuizBySolvedIs(true);
                prevStatus = SmeltStatus.SOLVED;
                smelt = createSmeltWith(senderInventory, smeltType, receiverFishingSpot, letter, quiz, prevStatus);
            }

            @DisplayName("받은 사람이 읽으면 상태가 READ로 바뀐다")
            @Test
            void shouldChangeStatus_readByReceiver() {
                smelt.readLetter(receiver);

                assertThat(smelt.getStatus()).isEqualTo(SmeltStatus.READ);
            }

            @DisplayName("보낸 사람이 읽으면 상태가 바뀌지 않는다")
            @Test
            void shouldNotChangeStatus_readBySender() {
                smelt.readLetter(sender);

                assertThat(smelt.getStatus()).isEqualTo(prevStatus);
            }

            @DisplayName("제 3자가 읽을 수 없다")
            @Test
            void fail_readByOther() {
                assertThatThrownBy(() -> smelt.readLetter(other))
                        .isInstanceOf(FishermanException.class)
                        .extracting("errorCode")
                        .isEqualTo(SmeltErrorCode.FORBIDDEN);
            }
        }

        @DisplayName("이미 읽었던 편지일 때")
        @Nested
        class AlreadyReadLetter {
            private SmeltStatus prevStatus;
            private Smelt smelt;

            @BeforeEach
            void initializeSmelt() throws NoSuchFieldException, IllegalAccessException {
                Letter letter = createLetterWith(1L, "sender");
                prevStatus = SmeltStatus.READ;
                smelt = createSmeltWith(senderInventory, smeltType, receiverFishingSpot, letter, prevStatus);
            }

            @DisplayName("받은 사람이 읽을 수 있다")
            @Test
            void succeed_readByReceiver() {
                assertThatNoException().isThrownBy(() -> smelt.readLetter(receiver));
            }

            @DisplayName("보낸 사람이 읽을 수 있다")
            @Test
            void succeed_readBySender() {
                assertThatNoException().isThrownBy(() -> smelt.readLetter(sender));
            }

            @DisplayName("제 3자가 읽을 수 없다")
            @Test
            void fail_readByOther(){
                assertThatThrownBy(() -> smelt.readLetter(other))
                        .isInstanceOf(FishermanException.class)
                        .extracting("errorCode")
                        .isEqualTo(SmeltErrorCode.FORBIDDEN);
            }
        }

        @DisplayName("받은 사람이 아직 퀴즈를 풀지 않았을 때")
        @Nested
        class UnsolvedLetter {
            private SmeltStatus prevStatus;
            private Smelt smelt;

            @BeforeEach
            void initializeSmelt() throws NoSuchFieldException, IllegalAccessException {
                Letter letter = createLetterWith(1L, "sender");
                Quiz quiz = createQuizBySolvedIs(false);
                prevStatus = SmeltStatus.UNREAD;
                smelt = createSmeltWith(senderInventory, smeltType, receiverFishingSpot, letter, quiz, prevStatus);
            }

            @DisplayName("받은 사람은 읽을 수 없다")
            @Test
            void fail_readByReceiver() {
                assertThatThrownBy(() -> smelt.readLetter(receiver))
                        .isInstanceOf(FishermanException.class)
                        .extracting("errorCode")
                        .isEqualTo(SmeltErrorCode.YET_SOLVED);
            }

            @DisplayName("보낸 사람이 읽으면 상태가 바뀌지 않는다")
            @Test
            void shouldNotChangeStatus_readBySender() {
                smelt.readLetter(sender);

                assertThat(smelt.getStatus()).isEqualTo(prevStatus);
            }

            @DisplayName("제 3자가 읽을 수 없다")
            @Test
            void fail_readBySender() {
                assertThatThrownBy(() -> smelt.readLetter(other))
                        .isInstanceOf(FishermanException.class)
                        .extracting("errorCode")
                        .isEqualTo(SmeltErrorCode.FORBIDDEN);
            }
        }

        @DisplayName("아직 빙어를 보내지 않았을 때")
        @Nested
        class ReadYetSentLetter {
            private Smelt smelt;

            @BeforeEach
            void initializeSmelt() throws NoSuchFieldException, IllegalAccessException {
                smelt = createSmeltWith(senderInventory, smeltType);
            }

            @DisplayName("빙어 주인이 읽을 수 없다")
            @Test
            void fail_readByOwner() {
                assertThatThrownBy(() -> smelt.readLetter(sender))
                        .isInstanceOf(FishermanException.class)
                        .extracting("errorCode")
                        .isEqualTo(SmeltErrorCode.NOT_FOUND);
            }

            @DisplayName("제 3자가 읽을 수 없다")
            @Test
            void fail_readByOther() {
                assertThatThrownBy(() -> smelt.readLetter(other))
                        .isInstanceOf(FishermanException.class)
                        .extracting("errorCode")
                        .isEqualTo(SmeltErrorCode.NOT_FOUND);
            }
        }
    }

    @DisplayName("답변 등록 테스트")
    @Nested
    class RegisterCommentTest {
        private Inventory senderInventory;
        private FishingSpot receiverFishingSpot;

        @BeforeEach
        void initialize() {
            senderInventory = Inventory.of(sender);
            receiverFishingSpot = FishingSpot.of(receiver);
        }

        @DisplayName("정상적으로 주고 받은 빙어일 때")
        @Nested
        class NormalSmelt {
            private Smelt smelt;

            @BeforeEach
            void initialize() throws NoSuchFieldException, IllegalAccessException {
                Letter letter = createLetterWith(1L, "sender");
                smelt = createSmeltWith(senderInventory, smeltType, receiverFishingSpot, letter, SmeltStatus.READ);
            }

            @DisplayName("받은 사람은 답변을 작성할 수 있다")
            @Test
            void succeed_registerByReceiver() {
                Comment comment = Comment.of("test content");

                assertThatNoException().isThrownBy(() -> smelt.registerComment(receiver, comment));
            }

            @DisplayName("보낸 사람은 답변을 작성할 수 없다")
            @Test
            void fail_registerByReceiver() {
                Comment comment = Comment.of("test content");

                assertThatThrownBy(() -> smelt.registerComment(sender, comment))
                        .isInstanceOf(FishermanException.class)
                        .extracting("errorCode")
                        .isEqualTo(SmeltErrorCode.FORBIDDEN);
            }

            @DisplayName("제 3자는 답변을 작성할 수 없다")
            @Test
            void fail_registerByOther() {
                Comment comment = Comment.of("test content");

                assertThatThrownBy(() -> smelt.registerComment(other, comment))
                        .isInstanceOf(FishermanException.class)
                        .extracting("errorCode")
                        .isEqualTo(SmeltErrorCode.FORBIDDEN);
            }
        }

        @DisplayName("이미 답변이 등록된 빙어일 때")
        @Nested
        class AlreadyRegistered {
            private Smelt smelt;

            @BeforeEach
            void initialize() throws NoSuchFieldException, IllegalAccessException {
                Comment comment = Comment.of("comment");
                Letter letter = createLetterWith(1L, "sender", comment);
                smelt = createSmeltWith(senderInventory, smeltType, receiverFishingSpot, letter, SmeltStatus.READ);
            }

            @DisplayName("받은 사람이 답변을 등록할 수 없다")
            @Test
            void fail_registerByReceiver() {
                Comment comment = Comment.of("test content");

                assertThatThrownBy(() -> smelt.registerComment(receiver, comment))
                        .isInstanceOf(FishermanException.class)
                        .extracting("errorCode")
                        .isEqualTo(SmeltErrorCode.ALREADY_COMMENT);
            }

            @DisplayName("보낸 사람이 답변을 등록할 수 없다")
            @Test
            void fail_registerBySender() {
                Comment comment = Comment.of("test content");

                assertThatThrownBy(() -> smelt.registerComment(sender, comment))
                        .isInstanceOf(FishermanException.class)
                        .extracting("errorCode")
                        .isEqualTo(SmeltErrorCode.FORBIDDEN);
            }

            @DisplayName("제 3자가 답변을 등록할 수 없다")
            @Test
            void fail_registerByOther() {
                Comment comment = Comment.of("test content");

                assertThatThrownBy(() -> smelt.registerComment(other, comment))
                        .isInstanceOf(FishermanException.class)
                        .extracting("errorCode")
                        .isEqualTo(SmeltErrorCode.FORBIDDEN);
            }
        }

        @DisplayName("아직 읽지 않은 빙어일 때")
        @Nested
        class YetRead {
            private Smelt smelt;

            @BeforeEach
            void initialize() throws NoSuchFieldException, IllegalAccessException {
                Letter letter = createLetterWith(1L, "sender");
                smelt = createSmeltWith(senderInventory, smeltType, receiverFishingSpot, letter, SmeltStatus.UNREAD);
            }

            @DisplayName("받은 사람이 답변을 등록할 수 없다")
            @Test
            void fail_registerByReceiver() {
                Comment comment = Comment.of("test content");

                assertThatThrownBy(() -> smelt.registerComment(receiver, comment))
                        .isInstanceOf(FishermanException.class)
                        .extracting("errorCode")
                        .isEqualTo(SmeltErrorCode.YET_READ);
            }

            @DisplayName("보낸 사람이 답변을 등록할 수 없다")
            @Test
            void fail_registerBySender() {
                Comment comment = Comment.of("test content");

                assertThatThrownBy(() -> smelt.registerComment(sender, comment))
                        .isInstanceOf(FishermanException.class)
                        .extracting("errorCode")
                        .isEqualTo(SmeltErrorCode.FORBIDDEN);
            }

            @DisplayName("제 3자가 답변을 등록할 수 없다")
            @Test
            void fail_registerByOther() {
                Comment comment = Comment.of("test content");

                assertThatThrownBy(() -> smelt.registerComment(other, comment))
                        .isInstanceOf(FishermanException.class)
                        .extracting("errorCode")
                        .isEqualTo(SmeltErrorCode.FORBIDDEN);
            }
        }

        @DisplayName("아직 보내지 않은 빙어일 때")
        @Nested
        class YetSent {
            private Smelt smelt;

            @BeforeEach
            void initialize() throws NoSuchFieldException, IllegalAccessException {
                smelt = createSmeltWith(senderInventory, smeltType);
            }

            @DisplayName("빙어의 주인이 답변을 등록할 수 없다.")
            @Test
            void fail_registerByOwner() {
                Comment comment = Comment.of("test content");

                assertThatThrownBy(() -> smelt.registerComment(sender, comment))
                        .isInstanceOf(FishermanException.class)
                        .extracting("errorCode")
                        .isEqualTo(SmeltErrorCode.NOT_FOUND);
            }

            @DisplayName("제 3자가 답변을 등록할 수 없다.")
            @Test
            void fail_registerByOther() {
                Comment comment = Comment.of("test content");

                assertThatThrownBy(() -> smelt.registerComment(other, comment))
                        .isInstanceOf(FishermanException.class)
                        .extracting("errorCode")
                        .isEqualTo(SmeltErrorCode.NOT_FOUND);
            }
        }
    }

    private User createUserWith(Long id, String email, String password, String nickname) throws IllegalAccessException, NoSuchFieldException {
        User user = Mockito.spy(User.of(email, password, nickname));

        Field idField = User.class.getDeclaredField("id");
        idField.setAccessible(true);
        idField.set(user, id);

        return user;
    }

    private SmeltType createSmeltTypeMockId() throws NoSuchFieldException, IllegalAccessException {
        SmeltType smeltType = Mockito.spy(new SmeltType());

        Field idField = SmeltType.class.getDeclaredField("id");
        idField.setAccessible(true);
        idField.set(smeltType, 1L);

        return smeltType;
    }

    private Letter createLetterWith(Long id, String sender) throws NoSuchFieldException, IllegalAccessException {
        Letter letter = Letter.of("letter content", "test sender");

        Field idField = Letter.class.getDeclaredField("id");
        idField.setAccessible(true);
        idField.set(letter, id);

        return letter;
    }

    private Letter createLetterWith(Long id, String sender, Comment comment) throws NoSuchFieldException, IllegalAccessException {
        Letter letter = Letter.of("letter content", "test sender");

        Field idField = Letter.class.getDeclaredField("id");
        idField.setAccessible(true);
        idField.set(letter, id);

        Field commentField = Letter.class.getDeclaredField("comment");
        commentField.setAccessible(true);
        commentField.set(letter, comment);

        return letter;
    }

    private Quiz createQuizBySolvedIs(boolean flag) throws NoSuchFieldException, IllegalAccessException {
        Quiz quiz = Quiz.of("quiz title", QuizType.OX);

        Field isSolvedField = Quiz.class.getDeclaredField("isSolved");
        isSolvedField.setAccessible(true);
        isSolvedField.set(quiz, flag);

        return quiz;
    }

    private Smelt createSmeltWith(Inventory inventory, SmeltType smeltType) throws IllegalAccessException, NoSuchFieldException {
        Smelt smelt = Smelt.of(inventory, smeltType);

        Field statusField = Smelt.class.getDeclaredField("status");
        statusField.setAccessible(true);
        statusField.set(smelt, SmeltStatus.DREW);

        return smelt;
    }

    private Smelt createSmeltWith(Inventory inventory, SmeltType smeltType, FishingSpot fishingSpot, Letter letter, SmeltStatus status) throws IllegalAccessException, NoSuchFieldException {
        Smelt smelt = Smelt.of(inventory, smeltType);

        Field fishingSpotField = Smelt.class.getDeclaredField("fishingSpot");
        fishingSpotField.setAccessible(true);
        fishingSpotField.set(smelt, fishingSpot);

        Field letterField = Smelt.class.getDeclaredField("letter");
        letterField.setAccessible(true);
        letterField.set(smelt, letter);

        Field statusField = Smelt.class.getDeclaredField("status");
        statusField.setAccessible(true);
        statusField.set(smelt, status);

        return smelt;
    }

    private Smelt createSmeltWith(Inventory inventory, SmeltType smeltType, FishingSpot fishingSpot, Letter letter, Quiz quiz, SmeltStatus status) throws IllegalAccessException, NoSuchFieldException {
        Smelt smelt = Smelt.of(inventory, smeltType);

        Field fishingSpotField = Smelt.class.getDeclaredField("fishingSpot");
        fishingSpotField.setAccessible(true);
        fishingSpotField.set(smelt, fishingSpot);

        Field letterField = Smelt.class.getDeclaredField("letter");
        letterField.setAccessible(true);
        letterField.set(smelt, letter);

        Field quizField = Smelt.class.getDeclaredField("quiz");
        quizField.setAccessible(true);
        quizField.set(smelt, quiz);

        Field statusField = Smelt.class.getDeclaredField("status");
        statusField.setAccessible(true);
        statusField.set(smelt, status);

        return smelt;
    }
}
