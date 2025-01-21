package my.fisherman.fisherman.smelt.api.response;

import java.time.LocalDateTime;

public class SmeltResponse {

    public record Simple (
        SimpleSmeltDto smelt
    ) {}

    public record Detail (
        DetailSmeltDto smelt,
        LetterDto letter
    ) {}

    
    public record SimpleSmeltDto (
        Long id,
        Long smeltTypeId,
        String status   // TODO: String -> SmeltStatus
    ) {}

    public record DetailSmeltDto (
        Long id,
        Long senderId,
        Long receiverId,
        Long smeltTypeId,
        String status   // TODO: String -> SmeltStatus
    ) {}

    public record LetterDto (
        Long id,
        String title,
        String content,
        LocalDateTime createdTime,
        CommentDto comment
    ) {}

    public record CommentDto (
        Long id,
        String content,
        LocalDateTime createdTime
    ) {}
}
