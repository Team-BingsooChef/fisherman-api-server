package my.fisherman.fisherman.inventory.application.dto;

import my.fisherman.fisherman.fishingspot.domain.FishingSpot;
import my.fisherman.fisherman.inventory.domain.Inventory;
import my.fisherman.fisherman.smelt.domain.Smelt;
import my.fisherman.fisherman.smelt.domain.Letter;
import my.fisherman.fisherman.smelt.domain.SmeltStatus;
import my.fisherman.fisherman.smelt.domain.SmeltType;

public class InventoryInfo {
    public record SmeltInfo(
            Long id,
            Inventory inventory,
            FishingSpot fishingSpot,
            SmeltStatus status,
            SmeltType type,
            Letter letter
    ) {
        public static SmeltInfo from(Smelt smelt) {
            return new SmeltInfo(
                    smelt.getId(),
                    smelt.getInventory(),
                    smelt.getFishingSpot(),
                    smelt.getStatus(),
                    smelt.getType(),
                    smelt.getLetter()
            );
        }
    }
}
