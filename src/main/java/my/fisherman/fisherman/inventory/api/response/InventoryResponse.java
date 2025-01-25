package my.fisherman.fisherman.inventory.api.response;

import my.fisherman.fisherman.smelt.domain.Comment;

import java.time.LocalDateTime;
import java.util.List;

public class InventoryResponse {

    public record DrawedSmelt(
            SmeltSimple smelt
    ) {}

    public record SentSmeltPage(
            String nickname,
            int page,
            int total,
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
    ) {}

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
