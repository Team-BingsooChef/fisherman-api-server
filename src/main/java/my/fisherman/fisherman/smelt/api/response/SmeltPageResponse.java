package my.fisherman.fisherman.smelt.api.response;

import java.util.List;

import lombok.Getter;

@Getter
public class SmeltPageResponse {
    private int page;
    private int total;
    private List<SmeltDto> smelts;

    @Getter
    class SmeltDto {
        private Long id;
        private Long senderId;
        private Long receiverId;
        private Long smeltTypeId;
    }
}
