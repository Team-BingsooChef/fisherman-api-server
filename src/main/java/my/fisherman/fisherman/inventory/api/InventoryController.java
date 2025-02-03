package my.fisherman.fisherman.inventory.api;

import my.fisherman.fisherman.inventory.api.response.InventoryResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/inventories/{inventory-id}/smelts")
@RestController
public class InventoryController implements InventorySpecification {

    @Override
    @PostMapping
    public ResponseEntity<InventoryResponse.DrewSmelt> drawSmelt(@PathVariable(name = "inventory-id") Long inventoryId) {
        // TODO
        return null;
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
