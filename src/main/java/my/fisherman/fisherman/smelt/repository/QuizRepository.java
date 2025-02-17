package my.fisherman.fisherman.smelt.repository;

import my.fisherman.fisherman.smelt.domain.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuizRepository extends JpaRepository<Quiz, Long> {
}
