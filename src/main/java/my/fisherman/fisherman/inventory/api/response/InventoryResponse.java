package my.fisherman.fisherman.inventory.api.response;

import java.time.LocalDateTime;
import java.util.List;

import my.fisherman.fisherman.inventory.application.dto.InventoryInfo;
import my.fisherman.fisherman.inventory.application.dto.InventoryInfo.SmeltInfo;

public class InventoryResponse {

    public record DrewSmelt(
            SmeltSimple smelt
    ) {
        public static DrewSmelt from(InventoryInfo.SmeltInfo smeltInfo) {
                return new DrewSmelt(SmeltSimple.from(smeltInfo));
        }
    }

    public record SentSmeltPage(
            String nickname,
            int currPage,
            int totalPages,
            int totalElements,
            List<SmeltDetail> smelts
    ) {}

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
    ) {
        static SmeltSimple from(SmeltInfo info) {
                return new SmeltSimple(info.id(), info.inventoryId(), info.smeltTypeId(), info.status());
        }
    }

    record SmeltDetail(
            Long id,
            Long inventoryId,
            Long fishingSpotId,
            Long smeltTypeId,
            String status,
            Letter letter
    ) {}

    record Letter(
            Long id,
            String senderName,
            String title,
            String content,
            String createdTime,
            Comment comment
    ) {}

    record Comment (
            Long id,
            String content,
            LocalDateTime createdTime
    ) {}
}
