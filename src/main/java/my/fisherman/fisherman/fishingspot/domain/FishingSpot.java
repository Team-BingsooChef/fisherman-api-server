package my.fisherman.fisherman.fishingspot.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import my.fisherman.fisherman.user.domain.User;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FishingSpot {

    @Id
    @Column(name = "fishing_spot_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Boolean isPublic;

    @JoinColumn(name = "fisherman_id", nullable = false)
    @OneToOne(fetch = FetchType.LAZY)
    private User fisherman;

    private FishingSpot(User fisherman) {
        this.id = null;
        this.fisherman = fisherman;
        this.isPublic = true;
    }

    public static FishingSpot of(User fisherman) {
        return new FishingSpot(fisherman);
    }
}
