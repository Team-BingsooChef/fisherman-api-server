package my.fisherman.fisherman.smelt.api.response;

import java.util.List;

public class SmeltTypeResponse {

    public record All (
        List<SmeltTypeDto> smeltTypes
    ) {}
    
    public record Count (
        CountDto count
    ) {}

    public record SmeltTypeDto (
        Long id,
        String name,
        String image
    ) {}

    public record CountDto (
        Long semltTypeId,
        int number
    ) {}
}
