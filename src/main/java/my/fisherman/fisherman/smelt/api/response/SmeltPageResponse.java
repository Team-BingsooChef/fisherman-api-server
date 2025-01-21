package my.fisherman.fisherman.smelt.api.response;

import java.util.List;

public class SmeltPageResponse {

    public record Simple(
        String nickname,
        int page,
        int total,
        List<SimpleSmeltDto> smelts
    ) {}

    public record Detail (
        int page,
        int total,
        List<DetailSmeltDto> smelts
    ) {}


    public record SimpleSmeltDto (
        Long id,
        Long smeltTypeId,
        String status   // TODO: String -> SmeltStatus
    ) {}

    public record DetailSmeltDto (
        Long id,
        Long senderId,
        Long receiverId,
        Long smeltTypeId,
        String status   // TODO: String -> SmeltStatus
    ) {}
}
