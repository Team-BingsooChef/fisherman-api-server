package my.fisherman.fisherman.inventory.api.response;

import my.fisherman.fisherman.inventory.application.dto.InventoryInfo;

import java.util.List;

public class InventoryResponse {

    public record Inventory (
        Long id,
        Long coin
    ) {
        public static Inventory from(InventoryInfo.Simple info) {
            return new Inventory(info.id(), info.coin());
        }
    }

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
    ) {
        public static Statistic from(List<InventoryInfo.Statistic> infos) {
            return new Statistic(infos.stream().map(Count::from).toList());
        }
    }

    record Count(
            Long smeltTypeId,
            Long count
    ) {
        static Count from(InventoryInfo.Statistic info) {
            return new Count(info.smeltTypeId(), info.count());
        }
    }

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
        static SmeltDetail from(InventoryInfo.DetailSmelt info) {
            return new SmeltDetail(
                    info.smeltInfo().id(),
                    info.smeltInfo().inventoryId(),
                    info.smeltInfo().fishingSpotId(),
                    info.nickname(),
                    info.smeltInfo().smeltTypeId(),
                    info.smeltInfo().status().name(),
                    info.letterInfo() != null ? Letter.from(info.letterInfo()) : null
            );
        }
    }

    record Letter(
            Long id,
            String senderName,
            String title,
            String createdTime,
            Comment comment
    ) {
        static Letter from(InventoryInfo.LetterInfo info) {
            return new Letter(
                    info.id(),
                    info.senderName(),
                    info.title(),
                    info.createdTime().toString(),
                    info.commentInfo() != null ? Comment.from(info.commentInfo()) : null
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
