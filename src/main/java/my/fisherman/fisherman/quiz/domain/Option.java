package my.fisherman.fisherman.quiz.domain;

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
public class Option {

    @Column(name = "question_id")
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "optiond_content", nullable = false)
    private String content;
    
    @Column(nullable = false)
    private Boolean isAnwer;

    @JoinColumn(name = "quiz_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Quiz quiz;
}
