package my.fisherman.fisherman.smelt.repository;

import my.fisherman.fisherman.inventory.domain.Inventory;
import my.fisherman.fisherman.smelt.domain.Smelt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SmeltRepository extends JpaRepository<Smelt, Long> {
    List<Smelt> findAllByInventoryIs(Inventory inventory);
}
