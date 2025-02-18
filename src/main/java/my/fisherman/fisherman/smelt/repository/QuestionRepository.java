package my.fisherman.fisherman.smelt.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import my.fisherman.fisherman.smelt.domain.Question;
import my.fisherman.fisherman.smelt.domain.Quiz;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
    List<Question> findAllByQuiz(Quiz quiz);
}
