package my.fisherman.fisherman.smelt.api.response;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Getter;

@Getter
public class SmeltDetailResponse {
    private SmeltDto smelt;
    private LetterDto letter;
    private CommentDto comment;
    private QuizDto quizDto;

    @Getter
    class SmeltDto {
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
    }

    @Getter
    class CommentDto {
        private Long id;
        private String content;
        private LocalDateTime createdTime;
    }

    @Getter
    class QuizDto {
        private Long id;
        private String title;
        private List<QuestionDto> questions;
        private String quizType;   // TODO: String -> QuizType
        private Short wrongCount;
        private Boolean isSolved;
    }

    @Getter
    class QuestionDto {
        private String content;
        private Boolean isAndwer;
    }
}
