package my.fisherman.fisherman.smelt.api.response;

import java.util.List;

public class FishingSpotResponse {

    public record Page(
        String nickname,
        int page,
        int total,
        List<SmeltDto> smelts
    ) {}

    public record SmeltDto (
        Long id,
        Long smeltTypeId,
        String status,   // TODO: String -> SmeltStatus
        Long quizId
    ) {}
}
