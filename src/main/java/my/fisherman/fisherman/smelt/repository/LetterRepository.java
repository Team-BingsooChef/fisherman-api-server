package my.fisherman.fisherman.smelt.repository;

import my.fisherman.fisherman.smelt.domain.Letter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LetterRepository extends JpaRepository<Letter, Long> {
}
