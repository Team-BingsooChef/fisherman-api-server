package my.fisherman.fisherman.quiz.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import my.fisherman.fisherman.quiz.api.request.SolveQuizRequest;
import my.fisherman.fisherman.quiz.api.response.QuizResponse;
import my.fisherman.fisherman.quiz.api.response.SolveQuizResponse;

@RestController
public class QuizController implements QuizControllerInterface{

    @Override
    @GetMapping("/smelts/{smelt-id}/quizzes")
    public ResponseEntity<QuizResponse> solveQuiz(@PathVariable(name = "smelt-id") Long smeltId) {
        // TODO
        return null;
    }

    @Override
    @PatchMapping("/smelts/quizzes")
    public ResponseEntity<SolveQuizResponse> solveQuiz(@RequestBody SolveQuizRequest request) {
        // TODO
        return null;
    }
}
