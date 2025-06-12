package my.fisherman.fisherman.smelt.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Question {

    @Column(name = "question_id")
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "question_content", nullable = false)
    private String content;
    
    @Column(nullable = false)
    private Boolean isAnswer;

    @JoinColumn(name = "quiz_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Quiz quiz;

    private Question(String content, Boolean isAnswer, Quiz quiz) {
        this.content = content;
        this.isAnswer = isAnswer;
        this.quiz = quiz;
    }

    public static Question of(String content, Boolean isAnswer, Quiz quiz) {
        return new Question(content, isAnswer, quiz);
    }
}
