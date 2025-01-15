package my.fisherman.fisherman.smelt.api.request;

import lombok.Getter;

@Getter
public class SendSmeltRequest {
    Long smeltId;
    LetterDto letter;

    @Getter
    class LetterDto {
        String title;
        String content;
    }
}
