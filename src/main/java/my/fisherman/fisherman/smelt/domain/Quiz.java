package my.fisherman.fisherman.smelt.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import my.fisherman.fisherman.global.exception.FishermanException;
import my.fisherman.fisherman.global.exception.code.SmeltErrorCode;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Quiz {

    @Column(name = "quiz_id")
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "quiz_title", nullable = false)
    private String title;

    @Column(name = "quiz_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private QuizType type;

    @Column(nullable = false)
    private Short wrongCount;

    @Column(nullable = false)
    private Boolean isSolved;

    private Quiz(String title, QuizType type) {
        this.id = null;
        this.title = title;
        this.type = type;
        this.wrongCount = 0;
        this.isSolved = false;
    }

    public static Quiz of(String title, QuizType type) {
        return new Quiz(title, type);
    }

    protected void trySolve(Question question) {
        if (this.isSolved) {
            throw new FishermanException(SmeltErrorCode.ALREADY_SOLVED);
        }

        if (!this.equals(question.getQuiz())) {
            throw new FishermanException(SmeltErrorCode.BAD_QUESTION);
        }

        if (question.getIsAnswer()) {
            this.isSolved = true;
            return;
        }

        this.wrongCount++;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Quiz)) return false;

        Quiz other = (Quiz) o;
        return id != null && id.equals(other.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
