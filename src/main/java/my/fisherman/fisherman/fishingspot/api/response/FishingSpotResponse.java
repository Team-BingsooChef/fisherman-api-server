package my.fisherman.fisherman.fishingspot.api.response;

import java.util.List;
import my.fisherman.fisherman.fishingspot.application.dto.FishingSpotInfo;

public class FishingSpotResponse {

    public record ReceivedSmelt(
        DetailSmelt smelt,
        Letter letter
    ) {
        public static ReceivedSmelt from(FishingSpotInfo.DetailSmelt info) {
            return new ReceivedSmelt(
                DetailSmelt.from(info),
                Letter.from(info.letter()));
        }
    }

    public record Page(
        String nickname,
        int currPage,
        int totalPages,
        int totalElements,
        List<SimpleSmelt> smelts
    ) {
        public static Page from(FishingSpotInfo.SmeltPage info) {
            return new Page(info.nickname(), info.currentPage(), info.totalPages(), info.totalElements(), info.smelts().stream().map(SimpleSmelt::from).toList());
        }
    }

    record SimpleSmelt(
        Long id,
        Long smeltTypeId,
        String status
    ) {
        static SimpleSmelt from(FishingSpotInfo.SimpleSmelt info) {
            return new SimpleSmelt(info.id(), info.typeId(), info.status());
        }
    }

    record DetailSmelt(
        Long id,
        Long inventoryId,
        Long fishingSpotId,
        Long smeltTypeId,
        String status
    ) {
        static DetailSmelt from(FishingSpotInfo.DetailSmelt info) {
            return new DetailSmelt(info.id(), info.inventoryId(), info.fishingSpotId(), info.typeId(), info.status());
        }
    }

    record Letter(
        Long id,
        String senderName,
        String title,
        String content,
        String createdTime
    ) {
        static Letter from(FishingSpotInfo.LetterInfo info) {
            return new Letter(info.id(), info.senderName(), info.title(), info.content(), info.createdTime().toString());
        }
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
