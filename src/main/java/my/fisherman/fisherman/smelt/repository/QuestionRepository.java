package my.fisherman.fisherman.smelt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import my.fisherman.fisherman.smelt.domain.Question;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
}
