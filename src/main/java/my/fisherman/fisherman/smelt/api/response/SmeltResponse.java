package my.fisherman.fisherman.smelt.api.response;

import java.time.LocalDateTime;
import java.util.List;

import my.fisherman.fisherman.smelt.application.dto.SmeltInfo;

public class SmeltResponse {

    public record Detail (
        Smelt smelt,
        Letter letter
    ) {
        public static Detail from(SmeltInfo.Detail info) {
            return new Detail(Smelt.from(info), Letter.from(info.letter()));
        }
    }

    public record AllOfType(
            List<SmeltType> smeltTypes
    ) {
        public static AllOfType of(List<SmeltInfo.Type> info) {
            return new AllOfType(info.stream().map(SmeltType::from).toList());
        }
    }

    record Smelt (
        Long id,
        Long inventoryId,
        Long fishermanId,
        Long smeltTypeId,
        String status
    ) {
        static Smelt from(SmeltInfo.Detail info) {
            return new Smelt(info.smeltId(), info.inventoryId(), info.fishermanId(), info.smeltTypeId(), info.status());
        }
    }

    record Letter (
        Long id,
        String title,
        String content,
        String senderName,
        LocalDateTime createdTime,
        Comment comment
    ) {
        static Letter from(SmeltInfo.LetterInfo info) {
            return new Letter(info.id(), info.title(), info.content(), info.senderName(), info.createdTime(), Comment.from(info.comment()));
        }
    }

    record Comment (
        Long id,
        String content,
        LocalDateTime createdTime
    ) {
        public static Comment from(SmeltInfo.CommentInfo info) {
            return new Comment(info.id(), info.content(), info.createdTime());
        }
    }

    record SmeltType (
            Long id,
            String name,
            String imageUrl,
            String iceImageUrl,
            Integer probability
    ) {
        static SmeltType from(SmeltInfo.Type info) {
            return new SmeltType(info.id(), info.name(), info.imageUrl(), info.iceImageUrl(), info.probability());
        }
    }
}
