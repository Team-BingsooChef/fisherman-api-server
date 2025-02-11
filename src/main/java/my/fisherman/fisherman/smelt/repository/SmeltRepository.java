package my.fisherman.fisherman.smelt.repository;

import my.fisherman.fisherman.inventory.domain.Inventory;
import my.fisherman.fisherman.smelt.domain.Smelt;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SmeltRepository extends JpaRepository<Smelt, Long> {
    Page<Smelt> findAllByAndInventoryIsAndFishingSpotIsNotNull(Inventory inventory, Pageable pageable);
}
