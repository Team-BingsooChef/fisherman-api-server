package my.fisherman.fisherman.smelt.repository;

import my.fisherman.fisherman.smelt.domain.Smelt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SmeltRepository extends JpaRepository<Smelt, Long> {
}
