package my.fisherman.fisherman.fishingspot.application.dto;

import my.fisherman.fisherman.fishingspot.domain.FishingSpot;

public class FishingSpotInfo {

    public record Simple(
        Long fishingSpotId,
        String nickname
    ) {

        public static FishingSpotInfo.Simple from(
            FishingSpot fishingSpot
        ) {
            return new FishingSpotInfo.Simple(
                fishingSpot.getId(),
                fishingSpot.getFisherman().getNickname()
            );
        }
    }
}
