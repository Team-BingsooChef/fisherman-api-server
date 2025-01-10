package my.fisherman.fisherman.letter.domain;

import java.time.LocalDateTime;

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

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Letter {

    @Column(name = "letter_id")
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "letter_title", nullable = false)
    private String title;

    @Column(name = "letter_content", nullable = false)
    private String content;

    @Column(name = "letter_created_time", nullable = false)
    private LocalDateTime createdTime;

    @JoinColumn(name = "comment_id")
    @OneToOne(fetch = FetchType.LAZY)
    private Comment comment;
}
