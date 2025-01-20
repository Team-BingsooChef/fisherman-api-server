package my.fisherman.fisherman.quiz.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import my.fisherman.fisherman.quiz.api.request.SolveQuizRequest;
import my.fisherman.fisherman.quiz.api.response.QuizResponse;
import my.fisherman.fisherman.quiz.api.response.SolveQuizResponse;

@RequestMapping("/smelts/quizzes")
@RestController
public class QuizController implements QuizSpecification{

    @Override
    @GetMapping("/{quiz-id}")
    public ResponseEntity<QuizResponse> getQuiz(@PathVariable(name = "quiz-id") Long quizId) {
        // TODO
        return null;
    }

    @Override
    @PatchMapping
    public ResponseEntity<SolveQuizResponse> solveQuiz(@RequestBody SolveQuizRequest request) {
        // TODO
        return null;
    }
}
