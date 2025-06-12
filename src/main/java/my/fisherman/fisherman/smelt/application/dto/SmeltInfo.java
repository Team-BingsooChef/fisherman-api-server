package my.fisherman.fisherman.smelt.application.dto;

import java.time.LocalDateTime;

import my.fisherman.fisherman.smelt.domain.Smelt;
import my.fisherman.fisherman.smelt.domain.SmeltType;
import my.fisherman.fisherman.smelt.domain.Letter;
import my.fisherman.fisherman.smelt.domain.Comment;

public class SmeltInfo {
    public record Type(
        Long id,
        String name,
        String imageUrl,
        String iceImageUrl,
        Integer probability
    ) {
        public static Type from(SmeltType smeltType) {
            return new Type(smeltType.getId(), smeltType.getName(), smeltType.getImage(), smeltType.getIceImage(), smeltType.getProbability());
        }
    }

    public record Detail(
        Long smeltId,
        Long inventoryId,
        Long fishermanId,
        Long smeltTypeId,
        String status,
        LetterInfo letter
    ) {
        public static Detail from(Smelt smelt) {
            return new Detail(
                smelt.getId(),
                smelt.getInventory().getId(),
                smelt.getFishingSpot() == null ? null : smelt.getFishingSpot().getId(),
                smelt.getType().getId(),
                smelt.getStatus().toString(),
                LetterInfo.from(smelt.getLetter())
            );
        }
    }

    public record LetterInfo(
        Long id,
        String content,
        String senderName,
        LocalDateTime createdTime,
        CommentInfo comment
    ) {
        public static LetterInfo from(Letter letter) {
            if (letter == null) {
                return null;
            }

            return new LetterInfo(
                letter.getId(),
                letter.getContent(),
                letter.getSenderName(),
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
            if (comment == null) {
                return null;
            }

            return new CommentInfo(comment.getId(), comment.getContent(), comment.getCreatedTime());
        }
    }
}
