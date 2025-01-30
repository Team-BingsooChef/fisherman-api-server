package my.fisherman.fisherman.smelt.api.response;

import java.time.LocalDateTime;
import java.util.List;

public class SmeltResponse {

    public record Detail (
        Smelt smelt,
        Letter letter
    ) {
    }

    public record AllOfType(
            List<SmeltType> smeltTypes
    ) {
    }

    record Smelt (
        Long id,
        Long senderId,
        Long receiverId,
        Long smeltTypeId,
        String status
    ) {
    }

    record Letter (
        Long id,
        String title,
        String content,
        String senderName,
        LocalDateTime createdTime,
        Comment comment
    ) {
    }

    record Comment (
        Long id,
        String content,
        LocalDateTime createdTime
    ) {
    }

    record SmeltType (
            Long id,
            String name,
            String image,
            String iceImage
    ) {
    }
}
