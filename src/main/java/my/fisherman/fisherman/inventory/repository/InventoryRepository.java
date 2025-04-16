package my.fisherman.fisherman.inventory.repository;

import my.fisherman.fisherman.inventory.domain.Inventory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    Optional<Inventory> findByUserId(Long userId);
}
