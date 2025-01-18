package my.fisherman.fisherman.smelt.api.response;

import java.util.List;

import lombok.Getter;

@Getter
public class FishingSpotResponse {

    @Getter
    public class Page {
        private String nickname;
        private int page;
        private int total;
        private List<SmeltDto> smelts;
    }

    @Getter
    class SmeltDto {
        private Long id;
        private Long smeltTypeId;
        private String status;   // TODO: String -> SmeltStatus
        private Long quizId;
    }
}
