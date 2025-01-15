package my.fisherman.fisherman.smelt.api.response;

import java.util.List;

import lombok.Getter;

@Getter
public class SmeltTypeCountResponse {
    private List<SmeltTypeCount> SmeltTypeCounts;

    @Getter
    class SmeltTypeCount {
        private Long smeltTypeId;
        private int count;
    }
}
