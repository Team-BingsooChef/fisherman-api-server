package my.fisherman.fisherman.fishingspot.application.dto;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;

import my.fisherman.fisherman.fishingspot.domain.FishingSpot;
import my.fisherman.fisherman.smelt.domain.Letter;
import my.fisherman.fisherman.smelt.domain.Question;
import my.fisherman.fisherman.smelt.domain.Quiz;
import my.fisherman.fisherman.smelt.domain.Smelt;

public class FishingSpotInfo {

    public record Simple(
        Long fishingSpotId,
        String nickname
    ) {
        public static FishingSpotInfo.Simple from(
            FishingSpot fishingSpot
        ) {
            return new FishingSpotInfo.Simple(
                fishingSpot.getId(),
                fishingSpot.getFisherman().getNickname()
            );
        }
    }

    public record SmeltPage(
        String nickname,
        Integer currentPage,
        Integer totalPages,
        Integer totalElements,
        List<SimpleSmelt> smelts
    ){
        public static SmeltPage of(FishingSpot fishingSpot, Page<Smelt> smeltPage) {
            return new SmeltPage(
                fishingSpot.getFisherman().getNickname(),
                smeltPage.getNumber(),
                smeltPage.getTotalPages(),
                (int) smeltPage.getTotalElements(),
                smeltPage.getContent().stream().map(SimpleSmelt::from).toList()
            );
        }
    }

    public record SimpleSmelt(
        Long id,
        Long typeId,
        String status
    ) {
        public static SimpleSmelt from(Smelt smelt) {
            return new SimpleSmelt(smelt.getId(), smelt.getType().getId(), smelt.getStatus().name());
        }
    }

    public record DetailSmelt(
        Long id,
        Long inventoryId,
        Long fishingSpotId,
        Long typeId,
        String status,
        LetterInfo letter,
        QuizInfo quiz
    ) {
        public static DetailSmelt of(Smelt smelt, List<Question> questions) {
            return new DetailSmelt(
                smelt.getId(), 
                smelt.getInventory().getId(),
                smelt.getFishingSpot().getId(),
                smelt.getType().getId(),
                smelt.getStatus().toString(),
                LetterInfo.from(smelt.getLetter()),
                QuizInfo.of(smelt.getQuiz(), questions));
        }
    }

    public record QuizInfo(
        Long id,
        String type,
        String title,
        List<QuestionInfo> questions
    ) {
        public static QuizInfo of(Quiz quiz, List<Question> questions) {
            if (quiz == null || questions == null) {
                return null;
            }

            return new QuizInfo(
                quiz.getId(),
                quiz.getType().name(),
                quiz.getTitle(),
                questions.stream().map(QuestionInfo::from).toList()
            );
        }
    }

    public record QuestionInfo(
        Long id,
        String content,
        Boolean isAnswer
    ) {
        public static QuestionInfo from(Question question) {
            return new QuestionInfo(question.getId(), question.getContent(), question.getIsAnswer());
        }
    }

    public record LetterInfo(
        Long id,
        String senderName,
        String title,
        String content,
        LocalDateTime createdTime
    ) {
        public static LetterInfo from(Letter letter) {
            return new LetterInfo(letter.getId(), letter.getSenderName(), letter.getTitle(), letter.getContent(), letter.getCreatedTime());
        }
    }
}
