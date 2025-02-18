package my.fisherman.fisherman.inventory.application.dto;

import my.fisherman.fisherman.inventory.domain.Inventory;
import my.fisherman.fisherman.smelt.domain.Comment;
import my.fisherman.fisherman.smelt.domain.Letter;
import my.fisherman.fisherman.smelt.domain.Smelt;
import my.fisherman.fisherman.smelt.domain.SmeltStatus;
import my.fisherman.fisherman.smelt.repository.dto.SmeltTypeCount;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.List;

public class InventoryInfo {
    public record Simple(
        Long id,
        Long coin
    ) {
        public static Simple from(Inventory inventory) {
            return new Simple(inventory.getId(), inventory.getCoin());
        }
    }

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
                    smelt.getFishingSpot() != null ? smelt.getFishingSpot().getId() : null,
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
            String nickname,
            SmeltInfo smeltInfo,
            LetterInfo letterInfo
    ) {
        public static DetailSmelt from(Smelt smelt) {
            return new DetailSmelt(
                    smelt.getFishingSpot() != null ? smelt.getFishingSpot().getFisherman().getNickname() : null,
                    SmeltInfo.from(smelt),
                    smelt.getLetter() != null ? LetterInfo.from(smelt.getLetter()) : null
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
                    letter.getComment() != null ? CommentInfo.from(letter.getComment()) : null
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

    public record Statistic(
            Long smeltTypeId,
            Long count
    ) {
        public static Statistic from(SmeltTypeCount count) {
            return new Statistic(count.typeId(), count.count());
        }
    }
}
