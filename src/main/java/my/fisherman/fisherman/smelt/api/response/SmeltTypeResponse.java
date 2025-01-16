package my.fisherman.fisherman.smelt.api.response;

import java.util.List;

import lombok.Getter;

@Getter
public class SmeltTypeResponse {

    @Getter
    public class All {
        List<SmeltTypeDto> smeltTypes;
    }
    
    @Getter
    public class Count {
        CountDto count;
    }

    @Getter
    class SmeltTypeDto {
        private Long id;
        private String name;
        private String image;
    }

    @Getter
    class CountDto {
        Long semltTypeId;
        int number;
    }
}
