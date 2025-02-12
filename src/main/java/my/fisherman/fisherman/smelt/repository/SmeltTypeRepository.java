package my.fisherman.fisherman.smelt.repository;

import my.fisherman.fisherman.smelt.domain.SmeltType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SmeltTypeRepository extends JpaRepository<SmeltType, Long> {
    List<SmeltType> findAllByOrderByCumulativeProbabilityAsc();
}
