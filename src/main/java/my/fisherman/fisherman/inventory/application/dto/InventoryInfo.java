package my.fisherman.fisherman.inventory.application.dto;

import java.time.LocalDateTime;

import my.fisherman.fisherman.smelt.domain.Smelt;
import my.fisherman.fisherman.smelt.domain.Letter;

public class InventoryInfo {
    public record SmeltInfo(
            Long id,
            Long inventoryId,
            Long fishingSpotId,
            String status,
            Long smeltTypeId,
            LetterInfo letter
    ) {
        public static SmeltInfo from(Smelt smelt) {
            return new SmeltInfo(
                    smelt.getId(),
                    smelt.getInventory().getId(),
                    smelt.getFishingSpot().getId(),
                    smelt.getStatus().toString(),
                    smelt.getType().getId(),
                    LetterInfo.from(smelt.getLetter())
            );
        }
    }

    record LetterInfo(
        Long id,
        String title,
        String content,
        String senderName,
        LocalDateTime createdTime
    ) {
        public static LetterInfo from(Letter letter) {
            return new LetterInfo(
                letter.getId(), 
                letter.getTitle(),
                letter.getContent(),
                letter.getSenderName(),
                letter.getCreatedTime());
        }
    }
}
