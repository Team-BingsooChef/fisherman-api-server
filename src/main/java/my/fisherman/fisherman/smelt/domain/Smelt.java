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
import my.fisherman.fisherman.letter.domain.Letter;
import my.fisherman.fisherman.quiz.domain.Quiz;
import my.fisherman.fisherman.user.domain.User;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Smelt {

    @Column(name = "smelt_id")
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "sender_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private User sender;

    @JoinColumn(name = "receiver_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private User receiver;

    @JoinColumn(name = "smelt_type_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private SmeltType type;

    @Column(name = "smelt_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private SmeltStatus status;

    @JoinColumn(name = "quiz_id")
    @OneToOne(fetch = FetchType.LAZY)
    private Quiz quiz;

    @JoinColumn(name = "letter_id")
    @OneToOne(fetch = FetchType.LAZY)
    private Letter letter;
}
