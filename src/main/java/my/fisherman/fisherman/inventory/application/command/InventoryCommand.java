package my.fisherman.fisherman.inventory.application.command;

public class InventoryCommand {
    public record DrawSmelt(
            Long userId,
            Long inventoryId
    ) {
    }
}
