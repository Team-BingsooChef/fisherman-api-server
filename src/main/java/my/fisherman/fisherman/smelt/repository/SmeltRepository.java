package my.fisherman.fisherman.smelt.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import my.fisherman.fisherman.smelt.domain.Smelt;

public interface SmeltRepository extends JpaRepository<Smelt, Long> {
}