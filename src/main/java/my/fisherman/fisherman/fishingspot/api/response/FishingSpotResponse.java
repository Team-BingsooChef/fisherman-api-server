package my.fisherman.fisherman.fishingspot.api.response;

import java.util.List;
import my.fisherman.fisherman.fishingspot.application.dto.FishingSpotInfo;

public class FishingSpotResponse {

    public record ReceivedSmelt(
        DetailSmelt smelt,
        Letter letter
    ) {

    }

    public record Page(
        String nickname,
        int currPage,
        int totalPages,
        int totalElements,
        List<SimpleSmelt> smelts
    ) {

    }

    record SimpleSmelt(
        Long id,
        Long smeltTypeId,
        String status
    ) {

    }

    record DetailSmelt(
        Long id,
        Long inventoryId,
        Long fishingSpotId,
        Long smeltTypeId,
        String status
    ) {

    }

    record Letter(
        Long id,
        String senderName,
        String title,
        String content,
        String createdTime
    ) {

    }

    public record FishingSpot(
        Long fishingSpotId,
        String nickname
    ) {

        public static FishingSpot from(FishingSpotInfo.Simple fishingSpot) {
            return new FishingSpot(
                fishingSpot.fishingSpotId(),
                fishingSpot.nickname()
            );
        }
    }
}
