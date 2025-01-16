package my.fisherman.fisherman.smelt.api.response;

import lombok.Getter;

@Getter
public class SmeltResponse {

    @Getter
    public class Simple {
        private Long id;
        private Long senderId;
        private Long smeltTypeId;
        private String status;
    }
}
