package my.fisherman.fisherman.inventory.domain;

import my.fisherman.fisherman.global.exception.FishermanException;
import my.fisherman.fisherman.global.exception.code.InventoryErrorCode;
import my.fisherman.fisherman.user.domain.User;
import my.fisherman.fisherman.util.TestFixtureUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("인벤토리 테스트")
public class InventoryTest {

    @DisplayName("코인을 얻는다")
    @ParameterizedTest(
            name = "코인 개수 {0}개에 대헤 정상적으로 코인을 얻는다"
    )
    @ValueSource(longs = {3L, 4L, 5L})
    void increaseCoin(long amount) {
        User user = TestFixtureUtil.createUserWith("test@email.com", "password", "nickname");
        Inventory inventory = TestFixtureUtil.createInventoryWith(user);
        Long givenCoin = inventory.getCoin();
        
        inventory.increaseCoin(amount);

        assertThat(inventory.getCoin()).isEqualTo(givenCoin + amount);
    }

    @DisplayName("코인을 얻을 때 오버플로우를 방지한다")
    @Test
    void increaseCoin_boundedMaxOfLong() {
        User user = TestFixtureUtil.createUserWith("test@email.com", "password", "nickname");
        Inventory inventory = TestFixtureUtil.createInventoryWith(user, Long.MAX_VALUE - 1);

        inventory.increaseCoin(5L);

        assertThat(inventory.getCoin()).isEqualTo(Long.MAX_VALUE);
    }

    @DisplayName("코인을 차감한다")
    @ParameterizedTest(
            name = "코인 개수 {0}개에 대헤 정상적으로 코인을 잃는다"
    )
    @ValueSource(longs = {5L, 10L})
    void decreaseCoin(long amount) {
        User user = TestFixtureUtil.createUserWith("test@email.com", "password", "nickname");
        Inventory inventory = TestFixtureUtil.createInventoryWith(user);
        Long givenCoin = inventory.getCoin();

        inventory.decreaseCoin(amount);

        assertThat(inventory.getCoin()).isEqualTo(givenCoin - amount);
    }

    @DisplayName("코인이 충분하지 않은 차감은 실패한다")
    @Test
    void fail_decreaseCoinByLackOfCoin() {
        User user = TestFixtureUtil.createUserWith("test@email.com", "password", "nickname");
        Inventory inventory = TestFixtureUtil.createInventoryWith(user, 1L);
        Long givenCoin = inventory.getCoin();

        // when & then: 코인 차감 실패
        assertThatThrownBy(() -> inventory.decreaseCoin(5L))
                .isInstanceOf(FishermanException.class)
                .extracting("errorCode")
                .isEqualTo(InventoryErrorCode.LACK_OF_COIN);

        // then: 코인 개수 유지
        assertThat(inventory.getCoin()).isEqualTo(givenCoin);
    }



}
