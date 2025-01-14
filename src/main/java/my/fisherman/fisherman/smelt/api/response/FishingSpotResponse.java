package my.fisherman.fisherman.smelt.api.response;

import java.util.List;

import lombok.Getter;

public class FishingSpotResponse {
    private int page;
    private int total;
    private List<SmeltDto> smelts;

    @Getter
    class SmeltDto {
        private Long id;
        private Long receiverId;
        private Long smeltTypeId;
        private String status;   // TODO: String -> SmeltStatus
    }
}
