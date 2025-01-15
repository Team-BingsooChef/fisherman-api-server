package my.fisherman.fisherman.quiz.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import my.fisherman.fisherman.quiz.api.request.SolveQuizRequest;
import my.fisherman.fisherman.quiz.api.response.SolveQuizResponse;

@RestController
public class QuizController implements QuizControllerInterface{

    @Override
    @PatchMapping("/smelts/quiz")
    public ResponseEntity<SolveQuizResponse> solveQuiz(@RequestBody SolveQuizRequest request) {
        // TODO
        return null;
    }

}
