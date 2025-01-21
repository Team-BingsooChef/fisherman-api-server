package my.fisherman.fisherman.smelt.api.request;

public class CommentRequest {
    
    public record Comment(
        String content
    ) {
    }
}
