package my.fisherman.fisherman.inventory.domain;

import jakarta.persistence.Embeddable;

@Embeddable
public class Coin {

    private Long coin;

    public Coin() {
        this.coin = 10L;
    }

    public Long getCoin() {
        return coin;
    }

    public void add(Long amount) {
        this.coin += amount;
    }

    public void sub(Long amount) {
        this.coin -= amount;
    }
}
