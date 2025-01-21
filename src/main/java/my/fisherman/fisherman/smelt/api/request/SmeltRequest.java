package my.fisherman.fisherman.smelt.api.request;

public class SmeltRequest {

    public record Send (
        Long receiverId,
        LetterDto letter
    ) {
    }

    public record LetterDto (
        String title,
        String content
    ) {
    }
}
