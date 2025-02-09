package my.fisherman.fisherman.inventory.api;

import lombok.RequiredArgsConstructor;
import my.fisherman.fisherman.inventory.api.response.InventoryResponse;
import my.fisherman.fisherman.inventory.application.InventoryService;
import my.fisherman.fisherman.inventory.application.dto.InventoryInfo;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/inventories/{inventory-id}/smelts")
@RestController
public class InventoryController implements InventorySpecification {
    private final InventoryService inventoryService;

    @Override
    @PostMapping
    public ResponseEntity<InventoryResponse.DrewSmelt> drawSmelt(@PathVariable(name = "inventory-id") Long inventoryId) {
        // TODO: 사용자 ID를 인증 정보에서 가져오도록 수정
        Long userId = 1L;

        InventoryInfo.SmeltInfo info = inventoryService.drawSmelt(userId, inventoryId);

        InventoryResponse.DrewSmelt response = InventoryResponse.DrewSmelt.from(info);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Override
    @GetMapping("/sent")
    public ResponseEntity<InventoryResponse.SentSmeltPage> getSentSmelt(@PathVariable(name = "inventory-id") Long inventoryId) {
        // TODO
    public ResponseEntity<InventoryResponse.SentSmeltPage> getSentSmelt(
            @PathVariable(name = "inventory-id") Long inventoryId,
            @PageableDefault(page = 0, size = 8) Pageable pageable
    ) {
        return null;
    }

    @Override
    @GetMapping("/statistics")
    public ResponseEntity<InventoryResponse.Statistic> getSmeltStatistic(@PathVariable(name = "inventory-id") Long inventoryId) {
        // TODO
        return null;
    }
}
