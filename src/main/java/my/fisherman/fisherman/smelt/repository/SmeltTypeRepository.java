package my.fisherman.fisherman.smelt.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import my.fisherman.fisherman.smelt.domain.SmeltType;

public interface SmeltTypeRepository extends JpaRepository<SmeltType, Long> {
    List<SmeltType> findAllOrderByCumulativeProbability();
}
