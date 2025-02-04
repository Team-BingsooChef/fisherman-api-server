package my.fisherman.fisherman.user.domain;

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

}
