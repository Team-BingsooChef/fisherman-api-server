package my.fisherman.fisherman.inventory.repository;

import my.fisherman.fisherman.inventory.domain.Inventory;

import my.fisherman.fisherman.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    Optional<Inventory> findByUser(User user);
}
