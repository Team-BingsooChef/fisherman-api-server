package my.fisherman.fisherman.smelt.api.request;

import org.hibernate.validator.constraints.Length;

public class SmeltRequest {

    public record RegisterComment(
        @Length(min = 1, max = 20) String content
    ) {
    }
}
