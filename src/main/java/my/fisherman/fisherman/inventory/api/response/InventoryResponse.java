package my.fisherman.fisherman.inventory.api.response;

import my.fisherman.fisherman.inventory.application.dto.InventoryInfo;

import java.util.List;

public class InventoryResponse {

    public record DrewSmelt(
            SmeltSimple smelt
    ) {
        public static DrewSmelt from(InventoryInfo.SmeltInfo info) {
            return new DrewSmelt(
                    new SmeltSimple(
                            info.id(),
                            info.inventoryId(),
                            info.smeltTypeId(),
                            info.status().name()
                    )
            );
        }
    }

    public record SentSmeltPage(
            int currPage,
            int totalPages,
            int totalElements,
            List<SmeltDetail> smelts
    ) {
        public static SentSmeltPage from(InventoryInfo.SentSmeltPage info) {
            return new SentSmeltPage(
                    info.currPage(),
                    info.totalPage(),
                    info.totalElement(),
                    info.smelts().stream().map(SmeltDetail::from).toList()
            );
        }
    }

    public record Statistic(
            List<Count> counts
    ) {}

    record Count(
            Integer count,
            Long smeltTypeId
    ) {}

    record SmeltSimple(
            Long id,
            Long inventoryId,
            Long smeltTypeId,
            String status
    ) {}

    record SmeltDetail(
            Long id,
            Long inventoryId,
            Long fishingSpotId,
            String fishermanNickname,
            Long smeltTypeId,
            String status,
            Letter letter
    ) {
        public static SmeltDetail from(InventoryInfo.DetailSmelt info) {
            return new SmeltDetail(
                    info.smeltInfo().id(),
                    info.smeltInfo().inventoryId(),
                    info.smeltInfo().fishingSpotId(),
                    info.nickName(),
                    info.smeltInfo().smeltTypeId(),
                    info.smeltInfo().status().name(),
                    Letter.from(info.letterInfo())
            );
        }
    }

    record Letter(
            Long id,
            String senderName,
            String title,
            String content,
            String createdTime,
            Comment comment
    ) {
        public static Letter from(InventoryInfo.LetterInfo info) {
            return new Letter(
                    info.id(),
                    info.senderName(),
                    info.title(),
                    info.content(),
                    info.createdTime().toString(),
                    Comment.from(info.commentInfo())
            );
        }
    }

    record Comment (
            Long id,
            String content,
            String createdTime
    ) {
        public static Comment from(InventoryInfo.CommentInfo info) {
            return new Comment(
                    info.id(),
                    info.content(),
                    info.createdTime().toString()
            );
        }
    }
}
