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

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/inventories/{inventory-id}/smelts")
@RestController
public class InventoryController implements InventorySpecification {
    private final InventoryService inventoryService;

    @Override
    @GetMapping("/mine")
    public ResponseEntity<InventoryResponse.Inventory> getMine() {
        InventoryInfo.Simple info = inventoryService.getMyInventory();

        InventoryResponse.Inventory response = InventoryResponse.Inventory.from(info);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Override
    @PostMapping
    public ResponseEntity<InventoryResponse.DrewSmelt> drawSmelt(@PathVariable(name = "inventory-id") Long inventoryId) {
        InventoryInfo.SmeltInfo info = inventoryService.drawSmelt(inventoryId);

        InventoryResponse.DrewSmelt response = InventoryResponse.DrewSmelt.from(info);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Override
    @GetMapping("/sent")
    public ResponseEntity<InventoryResponse.SentSmeltPage> getSentSmelt(
            @PathVariable(name = "inventory-id") Long inventoryId,
            @PageableDefault(page = 0, size = 8) Pageable pageable
    ) {
        InventoryInfo.SentSmeltPage info = inventoryService.searchSentSmelt(inventoryId, pageable);

        return ResponseEntity.status(HttpStatus.OK).body(InventoryResponse.SentSmeltPage.from(info));
    }

    @Override
    @GetMapping("/statistics")
    public ResponseEntity<InventoryResponse.Statistic> getSmeltStatistic(@PathVariable(name = "inventory-id") Long inventoryId) {
        List<InventoryInfo.Statistic> info = inventoryService.getStatistics(inventoryId);

        return ResponseEntity.status(HttpStatus.OK).body(InventoryResponse.Statistic.from(info));
    }
}
