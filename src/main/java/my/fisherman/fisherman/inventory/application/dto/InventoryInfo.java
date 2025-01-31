package my.fisherman.fisherman.inventory.application.dto;

import my.fisherman.fisherman.smelt.domain.Smelt;
import my.fisherman.fisherman.smelt.domain.SmeltStatus;

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
}
