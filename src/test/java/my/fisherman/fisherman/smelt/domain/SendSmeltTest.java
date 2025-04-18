package my.fisherman.fisherman.smelt.domain;


import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.doReturn;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import my.fisherman.fisherman.fishingspot.domain.FishingSpot;
import my.fisherman.fisherman.global.exception.FishermanException;
import my.fisherman.fisherman.inventory.domain.Inventory;
import my.fisherman.fisherman.user.domain.User;

import java.lang.reflect.Field;


class SendSmeltTest {
    private User user;
    private User sender;
    private User receiver;
    private User other;
    private SmeltType smeltType;
    private Letter letter;

    // 테스트에 사용할 의존성을 정상 상태로 생성
    @BeforeEach
    void initializeDependencies() {
        user = User.of("test@gmail.com", "test password", "test nickname");

        sender = Mockito.spy(user);
        doReturn(1L).when(sender).getId();

        receiver = Mockito.spy(user);
        doReturn(2L).when(receiver).getId();

        other = Mockito.spy(user);
        doReturn(3L).when(other).getId();

        smeltType = Mockito.spy(new SmeltType());
        doReturn(1L).when(smeltType).getId();

        letter = Letter.of("test content", "test sender");
    }

    @Test
    @DisplayName("빙어 보내기_퀴즈 없는 올바른 요청")
    void sendSmeltTest_withQuiz() {
        // given
        Inventory inventory = Inventory.of(sender);
        FishingSpot fishingSpot = FishingSpot.of(receiver);
        Smelt smelt = Smelt.of(inventory, smeltType);
        Quiz quiz = Quiz.of("title", QuizType.OX);

        // when & than
        assertThatNoException().isThrownBy(() -> smelt.send(inventory, fishingSpot, letter, quiz));
    }

    @Test
    @DisplayName("빙어 보내기_퀴즈 있는 올바른 요청")
    void sendSmeltTest_withoutQuiz() {
        // given
        Inventory inventory = Inventory.of(sender);
        FishingSpot fishingSpot = FishingSpot.of(receiver);
        Smelt smelt = Smelt.of(inventory, smeltType);

        // when & than
        assertThatNoException().isThrownBy(() -> smelt.send(inventory, fishingSpot, letter, null));
    }

    @Test
    @DisplayName("빙어 보내기_내 낚시터에 보내기")
    void sendSmeltTest_sendToMyFishingSpot() {
        // given
        Inventory inventory = Inventory.of(sender);
        FishingSpot fishingSpot = FishingSpot.of(sender);
        Smelt smelt = Smelt.of(inventory, smeltType);

        // when & than
        assertThatThrownBy(() -> smelt.send(inventory, fishingSpot, letter, null))
            .isInstanceOf(FishermanException.class);
    }

    @Test
    @DisplayName("빙어 보내기_타인의 빙어 보내기")
    void sendSmeltTest_sendSmeltOfOther() {
        // given
        Inventory inventory = Inventory.of(sender);
        FishingSpot fishingSpot = FishingSpot.of(receiver);
        Smelt smelt = Smelt.of(Inventory.of(other), smeltType);

        // when & than
        assertThatThrownBy(() -> smelt.send(inventory, fishingSpot, letter, null))
            .isInstanceOf(FishermanException.class);
    }

    @Test
    @DisplayName("빙어 보내기_이미 보낸 빙어 보내기")
    void sendSmeltTest_alreadySSentent() throws IllegalAccessException, NoSuchFieldException {
        // given
        Inventory inventory = Inventory.of(sender);
        FishingSpot fishingSpot = FishingSpot.of(receiver);
        Smelt smelt = Smelt.of(inventory, smeltType);

        Field fishingSpotField = Smelt.class.getDeclaredField("fishingSpot");
        fishingSpotField.setAccessible(true);
        fishingSpotField.set(smelt, fishingSpot);
        Field letterField = Smelt.class.getDeclaredField("letter");
        letterField.setAccessible(true);
        letterField.set(smelt, letter);
        Field statusField = Smelt.class.getDeclaredField("status");
        statusField.setAccessible(true);
        statusField.set(smelt, SmeltStatus.UNREAD);

        // when & than
        assertThatThrownBy(() -> smelt.send(inventory, fishingSpot, letter, null))
            .isInstanceOf(FishermanException.class);
    }
}

