package my.fisherman.fisherman.fishingspot.repository;

import java.util.List;
import my.fisherman.fisherman.fishingspot.domain.FishingSpot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface FishingSpotRepository extends JpaRepository<FishingSpot, Long> {

    @Query("""
          SELECT f FROM FishingSpot f
          JOIN FETCH User u ON f.fisherman.id = u.id
          WHERE u.nickname LIKE %:keyword%
        """)
    List<FishingSpot> searchByKeyword(String keyword);

}
