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
import my.fisherman.fisherman.global.exception.FishermanException;
import my.fisherman.fisherman.global.exception.code.SmeltErrorCode;
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

    // inventory에서 빙어를 fishingSpot으로 보낸다.
    public void send(Inventory inventory, FishingSpot fishingSpot, Letter letter, Quiz quiz) {
        if (inventory != this.inventory) {
            throw new FishermanException(SmeltErrorCode.FORBIDDEN, "자신이 뽑은 빙어만 보낼 수 있습니다.");
        }

        if (inventory.getUser() == fishingSpot.getFisherman()) {
            throw new FishermanException(SmeltErrorCode.NOT_MINE);
        }

        if (this.fishingSpot != null) {
            throw new FishermanException(SmeltErrorCode.ALREADY_SENT);
        }

        this.fishingSpot = fishingSpot;
        this.letter = letter;
        this.quiz = quiz;
        this.status = SmeltStatus.UNREAD;
    }

    public void registerComment(User user, Comment comment) {
        if (this.letter == null) {
            throw new FishermanException(SmeltErrorCode.NOT_FOUND, "빙어에 편지를 찾을 수 없습니다.");
        }

        if (user != this.fishingSpot.getFisherman()) {
            throw new FishermanException(SmeltErrorCode.FORBIDDEN, "자신이 받은 빙어에만 댓글을 남길 수 있습니다.");
        }

        if (this.status != SmeltStatus.READ) {
            throw new FishermanException(SmeltErrorCode.YET_READ, "빙어를 읽은 후 댓글을 남길 수 있습니다.");
        }

        this.letter.registerComment(comment);
    }

    public void readLetter(User user) {
        if (this.letter == null) {
            throw new FishermanException(SmeltErrorCode.NOT_FOUND, "편지가 없습니다.");
        }

        checkReadableLetter(user);

        // 자신이 보낸 빙어의 편지는 상태 변경 방지
        if (user == this.inventory.getUser()) {
            return;
        }

        this.status = SmeltStatus.READ;
    }

    public void trySolve(User user, Question question) {
        checkSolvable(user);

        if (this.quiz != question.getQuiz()) {
            throw new FishermanException(SmeltErrorCode.BAD_QUESTION);
        }

        if (this.quiz.getIsSolved()) {
            throw new FishermanException(SmeltErrorCode.ALREADY_SOLVED);
        }

        Boolean isCorrect = question.getIsAnswer();

        this.quiz.trySolve(isCorrect);
        this.status = isCorrect ? SmeltStatus.READ : this.status;
    }

    public void checkReadableQuiz(User user) {
        if (user == this.inventory.getUser() || user == this.fishingSpot.getFisherman()) {
            return;
        }

        throw new FishermanException(SmeltErrorCode.FORBIDDEN, "자신이 보냈거나 받은 빙어의 퀴즈만 볼 수 있습니다.");
    }

    private void checkReadableLetter(User user) {
        if (user.getId() == this.inventory.getUser().getId()) {
            return;
        }

        if (user.getId() == this.fishingSpot.getFisherman().getId()) {
            if (this.quiz != null && this.quiz.getIsSolved() == false) {
                throw new FishermanException(SmeltErrorCode.YET_SOLVED);
            }
            return;
        }

        throw new FishermanException(SmeltErrorCode.FORBIDDEN, "자신이 보냈거나 받은 빙어의 편지만 볼 수 있습니다.");
    }

    private void checkSolvable(User user) {
        if (user == this.fishingSpot.getFisherman()) {
            return;
        }

        throw new FishermanException(SmeltErrorCode.FORBIDDEN, "자신이 받은 빙어의 퀴즈만 풀 수 있습니다.");
    }
}
