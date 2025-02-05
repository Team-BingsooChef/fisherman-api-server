package my.fisherman.fisherman.inventory.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import my.fisherman.fisherman.inventory.domain.Inventory;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {
}
