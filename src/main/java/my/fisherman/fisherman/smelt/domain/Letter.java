package my.fisherman.fisherman.smelt.domain;

import java.time.LocalDateTime;

import jakarta.persistence.CascadeType;
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
import my.fisherman.fisherman.fishingspot.domain.FishingSpot;
import my.fisherman.fisherman.global.exception.FishermanException;
import my.fisherman.fisherman.global.exception.code.SmeltErrorCode;

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

    @Column(name = "sender_name", nullable = false)
    private String senderName;

    @Column(name = "letter_created_time", nullable = false)
    private LocalDateTime createdTime;

    @JoinColumn(name = "comment_id")
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private Comment comment;

    private Letter(String title, String content, String senderName) {
        this.id = null;
        this.title = title;
        this.content = content;
        this.senderName = senderName;
        this.createdTime = LocalDateTime.now();
        this.comment = null;
    }

    public static Letter of(String title, String content, String senderName) {
        return new Letter(title, content, senderName);
    }

    public void registerComment(Comment comment) {
        if (this.comment != null) {
            throw new FishermanException(SmeltErrorCode.ALREADY_COMMENT);
        }

        this.comment = comment;
    }
}
