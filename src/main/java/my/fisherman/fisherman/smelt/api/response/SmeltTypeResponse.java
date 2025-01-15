package my.fisherman.fisherman.smelt.api.response;

import java.util.List;

import lombok.Getter;

@Getter
public class SmeltTypeResponse {
    private List<SmeltTypeDto> smeltTypes;

    @Getter
    class SmeltTypeDto {
        private Long id;
        private String name;
        private String image;
    }
}
