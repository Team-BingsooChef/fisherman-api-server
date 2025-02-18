package my.fisherman.fisherman.smelt.repository;

import my.fisherman.fisherman.smelt.domain.Letter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LetterRepository extends JpaRepository<Letter, Long> {
}
