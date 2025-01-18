package my.fisherman.fisherman.smelt.api.response;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Getter;

@Getter
public class SmeltResponse {

    @Getter
    public class Simple {
        private SimpleSmeltDto smelt;
    }

    @Getter
    public class Detail {
        private DetailSmeltDto smelt;
        private LetterDto letter;
    }
    
    @Getter
    public class Page {
        private int page;
        private int total;
        private List<DetailSmeltDto> smelts;
    }

    @Getter
    class SimpleSmeltDto {
        private Long id;
        private Long smeltTypeId;
        private String status;   // TODO: String -> SmeltStatus
    }

    @Getter
    class DetailSmeltDto {
        private Long id;
        private Long senderId;
        private Long receiverId;
        private Long smeltTypeId;
        private String status;   // TODO: String -> SmeltStatus
    }

    @Getter
    class LetterDto {
        private Long id;
        private String title;
        private String content;
        private LocalDateTime createdTime;
        private CommentDto comment;
    }

    @Getter
    class CommentDto {
        private Long id;
        private String content;
        private LocalDateTime createdTime;
    }
}
