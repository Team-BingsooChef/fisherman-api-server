package my.fisherman.fisherman.smelt.api.request;

public class SendSmeltRequest {

    public record Letter(
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
