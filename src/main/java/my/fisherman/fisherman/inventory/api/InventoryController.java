package my.fisherman.fisherman.inventory.api;

import my.fisherman.fisherman.inventory.api.response.InventoryResponse;
import my.fisherman.fisherman.inventory.application.InventoryService;
import my.fisherman.fisherman.inventory.application.dto.InventoryInfo;
import my.fisherman.fisherman.security.util.SecurityUtil;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;

@RequestMapping("/inventories/{inventory-id}/smelts")
@RequiredArgsConstructor
@RestController
public class InventoryController implements InventorySpecification {
    private final InventoryService inventoryService;

    @Override
    @PostMapping
    public ResponseEntity<InventoryResponse.DrewSmelt> drawSmelt(@PathVariable(name = "inventory-id") Long inventoryId) {
        // TODO: ID 누락된 요청 예외 처리
        Long userId = SecurityUtil.getCurrentUserId().orElseThrow();

        InventoryInfo.SmeltInfo info = inventoryService.drawSmelt(inventoryId, userId);
        
        InventoryResponse.DrewSmelt response = InventoryResponse.DrewSmelt.from(info);
        
        return ResponseEntity.status(HttpStatusCode.valueOf(201)).body(response);
    }

    @Override
    @GetMapping("/sent")
    public ResponseEntity<InventoryResponse.SentSmeltPage> getSentSmelt(@PathVariable(name = "inventory-id") Long inventoryId) {
        // TODO
        return null;
    }

    @Override
    @GetMapping("/statistics")
    public ResponseEntity<InventoryResponse.Statistic> getSmeltStatistic(@PathVariable(name = "inventory-id") Long inventoryId) {
        // TODO
        return null;
    }
}
