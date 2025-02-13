package my.fisherman.fisherman.fishingspot.application.dto;

import java.util.List;

import org.springframework.data.domain.Page;

import my.fisherman.fisherman.fishingspot.domain.FishingSpot;
import my.fisherman.fisherman.smelt.domain.Smelt;

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

    public record SmeltPage(
        String nickname,
        Integer currentPage,
        Integer totalPages,
        Integer totalElements,
        List<SimpleSmelt> smelts
    ){
        public static SmeltPage of(FishingSpot fishingSpot, Page<Smelt> smeltPage) {
            return new SmeltPage(
                fishingSpot.getFisherman().getNickname(),
                smeltPage.getNumber(),
                smeltPage.getTotalPages(),
                (int) smeltPage.getTotalElements(),
                smeltPage.getContent().stream().map(SimpleSmelt::from).toList()
            );
        }
    }

    public record SimpleSmelt (
        Long id,
        Long typeId,
        String status
    ) {
        public static SimpleSmelt from(Smelt smelt) {
            return new SimpleSmelt(smelt.getId(), smelt.getType().getId(), smelt.getStatus().name());
        }
    }
}
