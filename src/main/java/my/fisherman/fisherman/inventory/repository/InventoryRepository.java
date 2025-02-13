package my.fisherman.fisherman.inventory.repository;

import my.fisherman.fisherman.inventory.domain.Inventory;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    Optional<Inventory> findByUser(Long userId);
}
