package my.fisherman.fisherman.smelt.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import my.fisherman.fisherman.fishingspot.domain.FishingSpot;
import my.fisherman.fisherman.inventory.domain.Inventory;
import my.fisherman.fisherman.user.domain.User;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Smelt {

    @Column(name = "smelt_id")
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "inventory_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Inventory inventory;

    @JoinColumn(name = "fishing_spot_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private FishingSpot fishingSpot;

    @Column(name = "smelt_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private SmeltStatus status;

    @JoinColumn(name = "smelt_type_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private SmeltType type;

    @JoinColumn(name = "quiz_id")
    @OneToOne(fetch = FetchType.LAZY)
    private Quiz quiz;

    @JoinColumn(name = "letter_id")
    @OneToOne(fetch = FetchType.LAZY)
    private Letter letter;

    private Smelt(Inventory inventory, SmeltType type) {
        this.inventory = inventory;
        this.type = type;
        this.status = SmeltStatus.DREW;
        this.fishingSpot = null;
        this.letter = null;
        this.quiz = null;
    }

    public static Smelt of(Inventory inventory, SmeltType type) {
        return new Smelt(inventory, type);
    }

    // user가 빙어를 fishingSpot으로 보낸다.
    public void send(User user, FishingSpot fishingSpot, Letter letter, Quiz quiz) {
        // TODO: ID 비교로 수정
        if (user != this.inventory.getUser()) {
            // TODO: 권한이 없는 경우 예외 던지기
            return;
        }

        if (user == fishingSpot.getFisherman()) {
            // TODO: 본인의 낚시터에 보내는 예외 던지기
            return;
        }

        this.fishingSpot = fishingSpot;
        this.letter = letter;
        this.quiz = quiz;
    }
}
