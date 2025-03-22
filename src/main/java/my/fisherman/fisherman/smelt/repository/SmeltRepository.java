package my.fisherman.fisherman.smelt.repository;

import my.fisherman.fisherman.fishingspot.domain.FishingSpot;
import my.fisherman.fisherman.inventory.domain.Inventory;
import my.fisherman.fisherman.smelt.domain.Smelt;
import my.fisherman.fisherman.smelt.domain.SmeltType;
import my.fisherman.fisherman.smelt.repository.dto.SmeltTypeCount;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SmeltRepository extends JpaRepository<Smelt, Long> {
    Page<Smelt> findAllByAndInventoryIsAndFishingSpotIsNotNull(Inventory inventory, Pageable pageable);

    Page<Smelt> findAllByFishingSpot(FishingSpot fishingSpot, Pageable pageable);

    @Query("select new my.fisherman.fisherman.smelt.repository.dto.SmeltTypeCount(s.type.id, count(*)) from Smelt s where s.inventory =:inventory group by s.type")
    List<SmeltTypeCount> countAllByInventoryIsGroupByType(Inventory inventory);

    @Query(value = "select s from Smelt s where s.inventory = :inventory and s.type = :smeltType and s.status = 'DREW' limit 1", nativeQuery = true)
    Optional<Smelt> findDrewSmeltByInventoryAndType(@Param("inventory") Inventory inventory, @Param("smeltType") SmeltType smeltType);
}
