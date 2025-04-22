package my.fisherman.fisherman.smelt.domain;

import my.fisherman.fisherman.fishingspot.domain.FishingSpot;
import my.fisherman.fisherman.global.exception.FishermanException;
import my.fisherman.fisherman.global.exception.code.SmeltErrorCode;
import my.fisherman.fisherman.inventory.domain.Inventory;
import my.fisherman.fisherman.user.domain.User;
import my.fisherman.fisherman.util.TestFixtureUtil;
import java.lang.reflect.InvocationTargetException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("빙어 도메인 테스트")
public class SmeltTest {

    @DisplayName("빙어 보내기 테스트")
    @Nested
    class SendSmeltTest {
        private User sender;
        private User receiver;
        private User other;
        private SmeltType smeltType;
        private Letter letter;

        @BeforeEach
        void initialize() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
            sender = TestFixtureUtil.createUserWith(1L, "test@gmail.com", "password", "sender");
            receiver = TestFixtureUtil.createUserWith(2L, "test@gmail.com", "password", "receiver");
            other = TestFixtureUtil.createUserWith(3L, "test@gmail.com", "password", "other");

            smeltType = TestFixtureUtil.createSmeltTypeWith(1L);
            letter = TestFixtureUtil.createLetterWith(1L, "test sender");
        }

        @Test
        @DisplayName("퀴즈 없이 보낼 수 있다")
        void succeed_withoutQuiz() {
            Inventory inventory = TestFixtureUtil.createInventoryWith(sender);
            FishingSpot fishingSpot = TestFixtureUtil.createFishingSpotWith(receiver);
            Smelt smelt = TestFixtureUtil.createSmeltWith(inventory, smeltType);

            smelt.send(inventory, fishingSpot, letter, null);

            assertThat(smelt.getStatus()).isEqualTo(SmeltStatus.UNREAD);
            assertThat(smelt.getLetter()).isEqualTo(letter);
        }

        @Test
        @DisplayName("퀴즈와 보낼 수 있다")
        void succeed_withQuiz() {
            Inventory inventory = TestFixtureUtil.createInventoryWith(sender);
            FishingSpot fishingSpot = TestFixtureUtil.createFishingSpotWith(receiver);
            Smelt smelt = TestFixtureUtil.createSmeltWith(inventory, smeltType);
            Quiz quiz = TestFixtureUtil.createQuizWith("title", QuizType.OX);

            smelt.send(inventory, fishingSpot, letter, quiz);

            assertThat(smelt.getStatus()).isEqualTo(SmeltStatus.UNREAD);
            assertThat(smelt.getLetter()).isEqualTo(letter);
            assertThat(smelt.getQuiz()).isEqualTo(quiz);
        }

        @Test
        @DisplayName("내 낚시터에 보낼 수 없다")
        void fail_toMyFishingSpot() {
            Inventory inventory = TestFixtureUtil.createInventoryWith(sender);
            FishingSpot fishingSpot = TestFixtureUtil.createFishingSpotWith(sender);
            Smelt smelt = TestFixtureUtil.createSmeltWith(inventory, smeltType);

            assertThatThrownBy(() -> smelt.send(inventory, fishingSpot, letter, null))
                    .isInstanceOf(FishermanException.class)
                    .extracting("errorCode")
                    .isEqualTo(SmeltErrorCode.NOT_MINE);
        }

        @Test
        @DisplayName("타인의 빙어를 보낼 수 없다")
        void fail_smeltOfOther() {
            Inventory otherInventory = TestFixtureUtil.createInventoryWith(other);
            FishingSpot fishingSpot = TestFixtureUtil.createFishingSpotWith(receiver);
            Smelt smelt = TestFixtureUtil.createSmeltWith(otherInventory, smeltType);
            Inventory senderInventory = TestFixtureUtil.createInventoryWith(sender);

            assertThatThrownBy(() -> smelt.send(senderInventory, fishingSpot, letter, null))
                    .isInstanceOf(FishermanException.class)
                    .extracting("errorCode")
                    .isEqualTo(SmeltErrorCode.FORBIDDEN);
        }

        @Test
        @DisplayName("이미 보냈던 빙어는 다시 보낼 수 없다")
        void fail_alreadySentSmelt() {
            Inventory inventory = TestFixtureUtil.createInventoryWith(sender);
            FishingSpot fishingSpot = TestFixtureUtil.createFishingSpotWith(receiver);
            Letter prevLetter = TestFixtureUtil.createLetterWith(2L, "Test letter");
            Smelt smelt = TestFixtureUtil.createSmeltWith(inventory, smeltType, fishingSpot, prevLetter, SmeltStatus.UNREAD);

            assertThatThrownBy(() -> smelt.send(inventory, fishingSpot, letter, null))
                    .isInstanceOf(FishermanException.class)
                    .extracting("errorCode")
                    .isEqualTo(SmeltErrorCode.ALREADY_SENT);
        }
    }

    @DisplayName("편지 읽기 테스트")
    @Nested
    class ReadLetterTest {

        @DisplayName("아직 받은 사람이 읽지 않은 퀴즈 없는 편지일 때")
        @Nested
        class FirstReadLetter {
            private User sender;
            private User receiver;
            private User other;
            private Smelt smelt;

            @BeforeEach
            void initialize() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
                sender = TestFixtureUtil.createUserWith(1L, "test@gmail.com", "password", "sender");
                receiver = TestFixtureUtil.createUserWith(2L, "test@gmail.com", "password", "receiver");
                other = TestFixtureUtil.createUserWith(3L, "test@gmail.com", "password", "other");

                Inventory senderInventory = TestFixtureUtil.createInventoryWith(sender);
                FishingSpot receiverFishingSpot = TestFixtureUtil.createFishingSpotWith(receiver);
                SmeltType smeltType = TestFixtureUtil.createSmeltTypeWith(1L);
                Letter letter = TestFixtureUtil.createLetterWith(1L, "sender");

                smelt = TestFixtureUtil.createSmeltWith(senderInventory, smeltType, receiverFishingSpot, letter, SmeltStatus.UNREAD);
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
                SmeltStatus prevStatus = smelt.getStatus();

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
            private User sender;
            private User receiver;
            private User other;
            private Smelt smelt;

            @BeforeEach
            void initialize() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
                sender = TestFixtureUtil.createUserWith(1L, "test@gmail.com", "password", "sender");
                receiver = TestFixtureUtil.createUserWith(2L, "test@gmail.com", "password", "receiver");
                other = TestFixtureUtil.createUserWith(3L, "test@gmail.com", "password", "other");

                Inventory senderInventory = TestFixtureUtil.createInventoryWith(sender);
                FishingSpot receiverFishingSpot = TestFixtureUtil.createFishingSpotWith(receiver);
                SmeltType smeltType = TestFixtureUtil.createSmeltTypeWith(1L);
                Letter letter = TestFixtureUtil.createLetterWith(1L, "sender");
                Quiz quiz = TestFixtureUtil.createQuizWith(true);

                smelt = TestFixtureUtil.createSmeltWith(senderInventory, smeltType, receiverFishingSpot, letter, quiz, SmeltStatus.SOLVED);
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
                SmeltStatus prevStatus = smelt.getStatus();

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
            private User sender;
            private User receiver;
            private User other;
            private Smelt smelt;

            @BeforeEach
            void initialize() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
                sender = TestFixtureUtil.createUserWith(1L, "test@gmail.com", "password", "sender");
                receiver = TestFixtureUtil.createUserWith(2L, "test@gmail.com", "password", "receiver");
                other = TestFixtureUtil.createUserWith(3L, "test@gmail.com", "password", "other");

                Inventory senderInventory = TestFixtureUtil.createInventoryWith(sender);
                FishingSpot receiverFishingSpot = TestFixtureUtil.createFishingSpotWith(receiver);
                SmeltType smeltType = TestFixtureUtil.createSmeltTypeWith(1L);
                Letter letter = TestFixtureUtil.createLetterWith(1L, "sender");

                smelt = TestFixtureUtil.createSmeltWith(senderInventory, smeltType, receiverFishingSpot, letter, SmeltStatus.READ);
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
            private User sender;
            private User receiver;
            private User other;
            private Smelt smelt;

            @BeforeEach
            void initialize() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
                sender = TestFixtureUtil.createUserWith(1L, "test@gmail.com", "password", "sender");
                receiver = TestFixtureUtil.createUserWith(2L, "test@gmail.com", "password", "receiver");
                other = TestFixtureUtil.createUserWith(3L, "test@gmail.com", "password", "other");

                Inventory senderInventory = TestFixtureUtil.createInventoryWith(sender);
                FishingSpot receiverFishingSpot = TestFixtureUtil.createFishingSpotWith(receiver);
                SmeltType smeltType = TestFixtureUtil.createSmeltTypeWith(1L);
                Letter letter = TestFixtureUtil.createLetterWith(1L, "sender");
                Quiz quiz = TestFixtureUtil.createQuizWith(false);

                smelt = TestFixtureUtil.createSmeltWith(senderInventory, smeltType, receiverFishingSpot, letter, quiz, SmeltStatus.UNREAD);
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
                SmeltStatus prevStatus = smelt.getStatus();

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
        class ReadYetSent {
            private User owner;
            private User other;
            private Smelt smelt;

            @BeforeEach
            void initializeSmelt() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
                owner = TestFixtureUtil.createUserWith(1L, "test@gmail.com", "password", "owner");
                other = TestFixtureUtil.createUserWith(2L, "test@gmail.com", "password", "other");

                Inventory ownerInventory = TestFixtureUtil.createInventoryWith(owner);
                SmeltType smeltType = TestFixtureUtil.createSmeltTypeWith(1L);

                smelt = TestFixtureUtil.createSmeltWith(ownerInventory, smeltType);
            }

            @DisplayName("빙어 주인이 읽을 수 없다")
            @Test
            void fail_readByOwner() {
                assertThatThrownBy(() -> smelt.readLetter(owner))
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

        @DisplayName("정상적으로 주고 받은 빙어일 때")
        @Nested
        class NormalSmelt {
            private User sender;
            private User receiver;
            private User other;
            private Smelt smelt;

            @BeforeEach
            void initialize() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
                sender = TestFixtureUtil.createUserWith(1L, "test@gmail.com", "password", "sender");
                receiver = TestFixtureUtil.createUserWith(2L, "test@gmail.com", "password", "receiver");
                other = TestFixtureUtil.createUserWith(3L, "test@gmail.com", "password", "other");

                Inventory senderInventory = TestFixtureUtil.createInventoryWith(sender);
                FishingSpot receiverFishingSpot = TestFixtureUtil.createFishingSpotWith(receiver);
                SmeltType smeltType = TestFixtureUtil.createSmeltTypeWith(1L);
                Letter letter = TestFixtureUtil.createLetterWith(1L, "sender");

                smelt = TestFixtureUtil.createSmeltWith(senderInventory, smeltType, receiverFishingSpot, letter, SmeltStatus.READ);
            }

            @DisplayName("받은 사람은 답변을 작성할 수 있다")
            @Test
            void succeed_registerByReceiver() {
                Comment comment = TestFixtureUtil.createCommentWith("test content");

                assertThatNoException().isThrownBy(() -> smelt.registerComment(receiver, comment));
            }

            @DisplayName("보낸 사람은 답변을 작성할 수 없다")
            @Test
            void fail_registerByReceiver() {
                Comment comment = TestFixtureUtil.createCommentWith("test content");

                assertThatThrownBy(() -> smelt.registerComment(sender, comment))
                        .isInstanceOf(FishermanException.class)
                        .extracting("errorCode")
                        .isEqualTo(SmeltErrorCode.FORBIDDEN);
            }

            @DisplayName("제 3자는 답변을 작성할 수 없다")
            @Test
            void fail_registerByOther() {
                Comment comment = TestFixtureUtil.createCommentWith("test content");

                assertThatThrownBy(() -> smelt.registerComment(other, comment))
                        .isInstanceOf(FishermanException.class)
                        .extracting("errorCode")
                        .isEqualTo(SmeltErrorCode.FORBIDDEN);
            }
        }

        @DisplayName("이미 답변이 등록된 빙어일 때")
        @Nested
        class AlreadyRegistered {
            private User sender;
            private User receiver;
            private User other;
            private Smelt smelt;

            @BeforeEach
            void initialize() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
                sender = TestFixtureUtil.createUserWith(1L, "test@gmail.com", "password", "sender");
                receiver = TestFixtureUtil.createUserWith(2L, "test@gmail.com", "password", "receiver");
                other = TestFixtureUtil.createUserWith(3L, "test@gmail.com", "password", "other");

                Inventory senderInventory = TestFixtureUtil.createInventoryWith(sender);
                FishingSpot receiverFishingSpot = TestFixtureUtil.createFishingSpotWith(receiver);
                SmeltType smeltType = TestFixtureUtil.createSmeltTypeWith(1L);
                Comment comment = TestFixtureUtil.createCommentWith("test comment");
                Letter letter = TestFixtureUtil.createLetterWith(1L, "sender", comment);

                smelt = TestFixtureUtil.createSmeltWith(senderInventory, smeltType, receiverFishingSpot, letter, SmeltStatus.READ);
            }

            @DisplayName("받은 사람이 답변을 등록할 수 없다")
            @Test
            void fail_registerByReceiver() {
                Comment comment = TestFixtureUtil.createCommentWith("test content");

                assertThatThrownBy(() -> smelt.registerComment(receiver, comment))
                        .isInstanceOf(FishermanException.class)
                        .extracting("errorCode")
                        .isEqualTo(SmeltErrorCode.ALREADY_COMMENT);
            }

            @DisplayName("보낸 사람이 답변을 등록할 수 없다")
            @Test
            void fail_registerBySender() {
                Comment comment = TestFixtureUtil.createCommentWith("test content");

                assertThatThrownBy(() -> smelt.registerComment(sender, comment))
                        .isInstanceOf(FishermanException.class)
                        .extracting("errorCode")
                        .isEqualTo(SmeltErrorCode.FORBIDDEN);
            }

            @DisplayName("제 3자가 답변을 등록할 수 없다")
            @Test
            void fail_registerByOther() {
                Comment comment = TestFixtureUtil.createCommentWith("test content");

                assertThatThrownBy(() -> smelt.registerComment(other, comment))
                        .isInstanceOf(FishermanException.class)
                        .extracting("errorCode")
                        .isEqualTo(SmeltErrorCode.FORBIDDEN);
            }
        }

        @DisplayName("아직 읽지 않은 빙어일 때")
        @Nested
        class YetRead {
            private User sender;
            private User receiver;
            private User other;
            private Smelt smelt;

            @BeforeEach
            void initialize() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
                sender = TestFixtureUtil.createUserWith(1L, "test@gmail.com", "password", "sender");
                receiver = TestFixtureUtil.createUserWith(2L, "test@gmail.com", "password", "receiver");
                other = TestFixtureUtil.createUserWith(3L, "test@gmail.com", "password", "other");

                Inventory senderInventory = TestFixtureUtil.createInventoryWith(sender);
                FishingSpot receiverFishingSpot = TestFixtureUtil.createFishingSpotWith(receiver);
                SmeltType smeltType = TestFixtureUtil.createSmeltTypeWith(1L);
                Letter letter = TestFixtureUtil.createLetterWith(1L, "sender");

                smelt = TestFixtureUtil.createSmeltWith(senderInventory, smeltType, receiverFishingSpot, letter, SmeltStatus.UNREAD);
            }

            @DisplayName("받은 사람이 답변을 등록할 수 없다")
            @Test
            void fail_registerByReceiver() {
                Comment comment = TestFixtureUtil.createCommentWith("test content");

                assertThatThrownBy(() -> smelt.registerComment(receiver, comment))
                        .isInstanceOf(FishermanException.class)
                        .extracting("errorCode")
                        .isEqualTo(SmeltErrorCode.YET_READ);
            }

            @DisplayName("보낸 사람이 답변을 등록할 수 없다")
            @Test
            void fail_registerBySender() {
                Comment comment = TestFixtureUtil.createCommentWith("test content");

                assertThatThrownBy(() -> smelt.registerComment(sender, comment))
                        .isInstanceOf(FishermanException.class)
                        .extracting("errorCode")
                        .isEqualTo(SmeltErrorCode.FORBIDDEN);
            }

            @DisplayName("제 3자가 답변을 등록할 수 없다")
            @Test
            void fail_registerByOther() {
                Comment comment = TestFixtureUtil.createCommentWith("test content");

                assertThatThrownBy(() -> smelt.registerComment(other, comment))
                        .isInstanceOf(FishermanException.class)
                        .extracting("errorCode")
                        .isEqualTo(SmeltErrorCode.FORBIDDEN);
            }
        }

        @DisplayName("아직 보내지 않은 빙어일 때")
        @Nested
        class YetSent {
            private User owner;
            private User other;
            private Smelt smelt;

            @BeforeEach
            void initialize() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
                owner = TestFixtureUtil.createUserWith(1L, "test@gmail.com", "password", "owner");
                other = TestFixtureUtil.createUserWith(2L, "test@gmail.com", "password", "other");

                Inventory ownerInventory = TestFixtureUtil.createInventoryWith(owner);
                SmeltType smeltType = TestFixtureUtil.createSmeltTypeWith(1L);

                smelt = TestFixtureUtil.createSmeltWith(ownerInventory, smeltType);
            }

            @DisplayName("빙어의 주인이 답변을 등록할 수 없다.")
            @Test
            void fail_registerByOwner() {
                Comment comment = TestFixtureUtil.createCommentWith("test content");

                assertThatThrownBy(() -> smelt.registerComment(owner, comment))
                        .isInstanceOf(FishermanException.class)
                        .extracting("errorCode")
                        .isEqualTo(SmeltErrorCode.NOT_FOUND);
            }

            @DisplayName("제 3자가 답변을 등록할 수 없다.")
            @Test
            void fail_registerByOther() {
                Comment comment = TestFixtureUtil.createCommentWith("test content");

                assertThatThrownBy(() -> smelt.registerComment(other, comment))
                        .isInstanceOf(FishermanException.class)
                        .extracting("errorCode")
                        .isEqualTo(SmeltErrorCode.NOT_FOUND);
            }
        }
    }

    @DisplayName("퀴즈 풀이 테스트 (권한에 따른 오류와 빙어 상태 변화)")
    @Nested
    class solveQuizTest {

        @DisplayName("아직 퀴즈를 풀지 않았을 때")
        @Nested
        class ExistQuiz {
            private User sender;
            private User receiver;
            private User other;
            private Smelt smelt;
            private Quiz quiz;

            @BeforeEach
            void initialize() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
                sender = TestFixtureUtil.createUserWith(1L, "test@gmail.com", "password", "sender");
                receiver = TestFixtureUtil.createUserWith(2L, "test@gmail.com", "password", "receiver");
                other = TestFixtureUtil.createUserWith(3L, "test@gmail.com", "password", "other");

                Inventory senderInventory = TestFixtureUtil.createInventoryWith(sender);
                FishingSpot receiverFishingSpot = TestFixtureUtil.createFishingSpotWith(receiver);
                SmeltType smeltType = TestFixtureUtil.createSmeltTypeWith(1L);
                Letter letter = TestFixtureUtil.createLetterWith(1L, "sender");
                quiz = TestFixtureUtil.createQuizWith(1L, false);

                smelt = TestFixtureUtil.createSmeltWith(senderInventory, smeltType, receiverFishingSpot, letter, quiz, SmeltStatus.UNREAD);
            }

            @DisplayName("받은 사람이 정답을 제출하면 퀴즈를 맞춘다")
            @Test
            void solve_tryByReceiverWithAnswerQuestion() {
                Question question = TestFixtureUtil.createQuestionWith("content", true, quiz);

                smelt.trySolve(receiver, question);

                assertThat(smelt.getStatus()).isEqualTo(SmeltStatus.SOLVED);
            }

            @DisplayName("받은 사람이 오답을 제출하면 퀴즈를 틀린다")
            @Test
            void wrong_tryByReceiverWithWrongQuestion() {
                Question question = TestFixtureUtil.createQuestionWith("content", false, quiz);
                SmeltStatus prevStatus = smelt.getStatus();

                smelt.trySolve(receiver, question);

                assertThat(smelt.getStatus()).isEqualTo(prevStatus);
            }

            @DisplayName("받은 사람이 잘못된 응답을 제출하면 실패한다")
            @Test
            void fail_tryByReceiverWithOtherQuestion() {
                Quiz otherQuiz = TestFixtureUtil.createQuizWith(quiz.getId() + 10L, false);
                Question question = TestFixtureUtil.createQuestionWith("content", false, otherQuiz);
                SmeltStatus prevStatus = smelt.getStatus();

                // then: 접근 실패
                assertThatThrownBy(() -> smelt.trySolve(receiver, question))
                        .isInstanceOf(FishermanException.class)
                        .extracting("errorCode")
                        .isEqualTo(SmeltErrorCode.BAD_QUESTION);

                // then: 상태 유지
                assertThat(smelt.getStatus()).isEqualTo(prevStatus);
            }

            @DisplayName("보낸 사람은 정답을 제출할 수 없다")
            @Test
            void fail_tryAnswerBySender() {
                Question question = TestFixtureUtil.createQuestionWith("content", true, quiz);
                SmeltStatus prevStatus = smelt.getStatus();

                // then: 접근 실패
                assertThatThrownBy(() -> smelt.trySolve(sender, question))
                        .isInstanceOf(FishermanException.class)
                        .extracting("errorCode")
                        .isEqualTo(SmeltErrorCode.FORBIDDEN);

                // then: 상태 유지
                assertThat(smelt.getStatus()).isEqualTo(prevStatus);
            }

            @DisplayName("보낸 사람은 오답을 제출할 수 없다")
            @Test
            void fail_tryWrongBySender() {
                Question question = TestFixtureUtil.createQuestionWith("content", false, quiz);
                SmeltStatus prevStatus = smelt.getStatus();

                // then: 접근 실패
                assertThatThrownBy(() -> smelt.trySolve(sender, question))
                        .isInstanceOf(FishermanException.class)
                        .extracting("errorCode")
                        .isEqualTo(SmeltErrorCode.FORBIDDEN);

                // then: 상태 유지
                assertThat(smelt.getStatus()).isEqualTo(prevStatus);
            }

            @DisplayName("제 3자는 정답을 제출할 수 없다")
            @Test
            void fail_tryAnswerByOther() {
                Question question = TestFixtureUtil.createQuestionWith("content", true, quiz);
                SmeltStatus prevStatus = smelt.getStatus();

                // then: 접근 실패
                assertThatThrownBy(() -> smelt.trySolve(other, question))
                        .isInstanceOf(FishermanException.class)
                        .extracting("errorCode")
                        .isEqualTo(SmeltErrorCode.FORBIDDEN);

                // then: 상태 유지
                assertThat(smelt.getStatus()).isEqualTo(prevStatus);
            }

            @DisplayName("제 3자는 오답을 제출할 수 없다")
            @Test
            void fail_tryWrongByOther() {
                Question question = TestFixtureUtil.createQuestionWith("content", false, quiz);
                SmeltStatus prevStatus = smelt.getStatus();

                // then: 접근 실패
                assertThatThrownBy(() -> smelt.trySolve(other, question))
                        .isInstanceOf(FishermanException.class)
                        .extracting("errorCode")
                        .isEqualTo(SmeltErrorCode.FORBIDDEN);

                // then: 상태 유지
                assertThat(smelt.getStatus()).isEqualTo(prevStatus);
            }
        }

        @DisplayName("이미 퀴즈를 풀었을 때")
        @Nested
        class NotExistQuiz {
            private User sender;
            private User receiver;
            private User other;
            private Smelt smelt;
            private Quiz quiz;

            @BeforeEach
            void initialize() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
                sender = TestFixtureUtil.createUserWith(1L, "test@gmail.com", "password", "sender");
                receiver = TestFixtureUtil.createUserWith(2L, "test@gmail.com", "password", "receiver");
                other = TestFixtureUtil.createUserWith(3L, "test@gmail.com", "password", "other");

                Inventory senderInventory = TestFixtureUtil.createInventoryWith(sender);
                FishingSpot receiverFishingSpot = TestFixtureUtil.createFishingSpotWith(receiver);
                SmeltType smeltType = TestFixtureUtil.createSmeltTypeWith(1L);
                Letter letter = TestFixtureUtil.createLetterWith(1L, "sender");
                quiz = TestFixtureUtil.createQuizWith(1L, true);

                smelt = TestFixtureUtil.createSmeltWith(senderInventory, smeltType, receiverFishingSpot, letter, quiz, SmeltStatus.SOLVED);
            }

            @DisplayName("받은 사람이 정답을 제출할 수 없다")
            @Test
            void fail_tryAnswerByReceiver() {
                Question question = TestFixtureUtil.createQuestionWith("content", true, quiz);

                assertThatThrownBy(() -> smelt.trySolve(receiver, question))
                        .isInstanceOf(FishermanException.class)
                        .extracting("errorCode")
                        .isEqualTo(SmeltErrorCode.ALREADY_SOLVED);
            }

            @DisplayName("받은 사람이 오답을 제출할 수 없다")
            @Test
            void fail_tryWrongByReceiver() {
                Question question = TestFixtureUtil.createQuestionWith("content", false, quiz);

                assertThatThrownBy(() -> smelt.trySolve(receiver, question))
                        .isInstanceOf(FishermanException.class)
                        .extracting("errorCode")
                        .isEqualTo(SmeltErrorCode.ALREADY_SOLVED);
            }

            @DisplayName("보낸 사람은 정답을 제출할 수 없다")
            @Test
            void fail_tryAnswerBySender() {
                Question question = TestFixtureUtil.createQuestionWith("content", true, quiz);
                SmeltStatus prevStatus = smelt.getStatus();

                // then: 접근 실패
                assertThatThrownBy(() -> smelt.trySolve(sender, question))
                        .isInstanceOf(FishermanException.class)
                        .extracting("errorCode")
                        .isEqualTo(SmeltErrorCode.FORBIDDEN);

                // then: 상태 유지
                assertThat(smelt.getStatus()).isEqualTo(prevStatus);
            }

            @DisplayName("보낸 사람은 오답을 제출할 수 없다")
            @Test
            void fail_tryWrongBySender() {
                Question question = TestFixtureUtil.createQuestionWith("content", false, quiz);
                SmeltStatus prevStatus = smelt.getStatus();

                // then: 접근 실패
                assertThatThrownBy(() -> smelt.trySolve(sender, question))
                        .isInstanceOf(FishermanException.class)
                        .extracting("errorCode")
                        .isEqualTo(SmeltErrorCode.FORBIDDEN);

                // then: 상태 유지
                assertThat(smelt.getStatus()).isEqualTo(prevStatus);
            }

            @DisplayName("제 3자는 정답을 제출할 수 없다")
            @Test
            void fail_tryAnswerByOther() {
                Question question = TestFixtureUtil.createQuestionWith("content", true, quiz);
                SmeltStatus prevStatus = smelt.getStatus();

                // then: 접근 실패
                assertThatThrownBy(() -> smelt.trySolve(other, question))
                        .isInstanceOf(FishermanException.class)
                        .extracting("errorCode")
                        .isEqualTo(SmeltErrorCode.FORBIDDEN);

                // then: 상태 유지
                assertThat(smelt.getStatus()).isEqualTo(prevStatus);
            }

            @DisplayName("제 3자는 오답을 제출할 수 없다")
            @Test
            void fail_tryWrongByOther() {
                Question question = TestFixtureUtil.createQuestionWith("content", false, quiz);
                SmeltStatus prevStatus = smelt.getStatus();

                // then: 접근 실패
                assertThatThrownBy(() -> smelt.trySolve(other, question))
                        .isInstanceOf(FishermanException.class)
                        .extracting("errorCode")
                        .isEqualTo(SmeltErrorCode.FORBIDDEN);

                // then: 상태 유지
                assertThat(smelt.getStatus()).isEqualTo(prevStatus);
            }
        }
    }
}
