package my.fisherman.fisherman.smelt.api.response;

import java.time.LocalDateTime;

import lombok.Getter;

@Getter
public class SendSmeltResponse {
    SmeltDto smelt;
    LetterDto letter;

    @Getter
    class SmeltDto {
        Long id;
        Long senderId;
        Long receiverId;
        Long smeltTypeId;
        private String status;  // TODO: String -> SmeltStatus
    }

    @Getter
    class LetterDto {
        Long id;
        String title;
        String content;
        LocalDateTime createdTime;
    }
}
