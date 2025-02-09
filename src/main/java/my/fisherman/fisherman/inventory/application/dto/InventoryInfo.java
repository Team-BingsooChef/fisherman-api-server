package my.fisherman.fisherman.inventory.application.dto;

import my.fisherman.fisherman.smelt.domain.Comment;
import my.fisherman.fisherman.smelt.domain.Letter;
import my.fisherman.fisherman.smelt.domain.Smelt;
import my.fisherman.fisherman.smelt.domain.SmeltStatus;

import java.time.LocalDateTime;

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

    public record DetailSmeltInfo(
            SmeltInfo smeltInfo,
            LetterInfo letterInfo,
            CommentInfo commentInfo
    ) {
        public static DetailSmeltInfo from(Smelt smelt) {
            return new DetailSmeltInfo(
                    SmeltInfo.from(smelt),
                    LetterInfo.from(smelt.getLetter()),
                    CommentInfo.from(smelt.getLetter().getComment())
            );
        }
    }

    record LetterInfo(
            Long id,
            String senderName,
            String title,
            LocalDateTime createdTime
    ) {
        public static LetterInfo from(Letter letter) {
            return new LetterInfo(
                    letter.getId(),
                    letter.getSenderName(),
                    letter.getTitle(),
                    letter.getCreatedTime()
            );
        }
    }

    record CommentInfo(
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
