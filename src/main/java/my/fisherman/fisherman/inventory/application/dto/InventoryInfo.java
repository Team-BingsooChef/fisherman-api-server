package my.fisherman.fisherman.inventory.application.dto;

import my.fisherman.fisherman.smelt.domain.Comment;
import my.fisherman.fisherman.smelt.domain.Letter;
import my.fisherman.fisherman.smelt.domain.Smelt;
import my.fisherman.fisherman.smelt.domain.SmeltStatus;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.List;

public class InventoryInfo {
    public record SmeltInfo(
            Long id,
            Long inventoryId,
            Long fishingSpotId,
            SmeltStatus status,
            Long smeltTypeId
    ) {
        public static SmeltInfo from(Smelt smelt) {
            return new SmeltInfo(
                    smelt.getId(),
                    smelt.getInventory().getId(),
                    smelt.getFishingSpot().getId(),
                    smelt.getStatus(),
                    smelt.getType().getId()
            );
        }
    }

    public record SentSmeltPage(
            Integer currPage,
            Integer totalPage,
            Integer totalElement,
            List<DetailSmelt> smelts
    ) {
        public static SentSmeltPage of(Page<Smelt> page) {
            return new SentSmeltPage(
                    page.getNumber(),
                    page.getTotalPages(),
                    (int) page.getTotalElements(),
                    page.getContent().stream().map(DetailSmelt::from).toList());
        }
    }

    public record DetailSmelt(
            String nickName,
            SmeltInfo smeltInfo,
            LetterInfo letterInfo
    ) {
        public static DetailSmelt from(Smelt smelt) {
            return new DetailSmelt(
                    smelt.getFishingSpot().getFisherman().getNickname(),
                    SmeltInfo.from(smelt),
                    LetterInfo.from(smelt.getLetter())
            );
        }
    }

    public record LetterInfo(
            Long id,
            String senderName,
            String title,
            String content,
            LocalDateTime createdTime,
            CommentInfo commentInfo
    ) {
        public static LetterInfo from(Letter letter) {
            return new LetterInfo(
                    letter.getId(),
                    letter.getSenderName(),
                    letter.getTitle(),
                    letter.getContent(),
                    letter.getCreatedTime(),
                    CommentInfo.from(letter.getComment())
            );
        }
    }

    public record CommentInfo(
            Long id,
            String content,
            LocalDateTime createdTime
    ) {
        public static CommentInfo from(Comment comment) {
            return new CommentInfo(
                    comment.getId(),
                    comment.getContent(),
                    comment.getCreatedTime()
            );
        }
    }
}
