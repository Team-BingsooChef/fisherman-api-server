package my.fisherman.fisherman.fishingspot.api.request;

import java.util.List;

public class FishingSpotRequest {

    public record Send(
            Long smeltId,
            String title,
            String content,
            String senderName,
            Quiz quiz
    ) {}

    record Quiz(
        String title,
        String content,
        String type,
        List<String> questions,
        int answerIndex
    ) {}
}
