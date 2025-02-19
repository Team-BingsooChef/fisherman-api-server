package my.fisherman.fisherman.inventory.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import my.fisherman.fisherman.global.exception.FishermanException;
import my.fisherman.fisherman.global.exception.code.InventoryErrorCode;
import my.fisherman.fisherman.user.domain.User;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Inventory {

    @Column(name = "inventory_id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "user_id", nullable = false)
    @OneToOne(fetch = FetchType.LAZY)
    private User user;

    @Embedded
    private Coin coin;

    private Inventory(User user) {
        this.user = user;
        this.coin = new Coin();
    }

    public static Inventory of(User user) {
        return new Inventory(user);
    }

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public Long getCoin() {
        return coin.getCoin();
    }

    public void increaseCoin(Long amount) {
        this.coin.add(amount);
    }

    public void decreaseCoin(Long amount) {
        if (this.coin.getCoin() < amount) {
            throw new FishermanException(InventoryErrorCode.LACK_OF_COIN, "현재 코인 %d개로, %d개의 코인이 부족합니다.".formatted(this.coin.getCoin(), amount - this.coin.getCoin()));
        }

        this.coin.sub(amount);
    }

    public void checkReadable(User user) {
        // TODO: ID 비교로 수정
        if (this.user == user) {
            return;
        }
        
        // TODO: 커스텀 예외 던지기
        throw new FishermanException(InventoryErrorCode.FORBIDDEN, "자신의 인벤토리만 볼 수 있습니다.");
    }
}
