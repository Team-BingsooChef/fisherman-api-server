package my.fisherman.fisherman.smelt.api;

import my.fisherman.fisherman.smelt.api.request.QuizRequest;
import my.fisherman.fisherman.smelt.api.request.SmeltRequest;
import my.fisherman.fisherman.smelt.api.response.QuizResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import my.fisherman.fisherman.smelt.api.response.SmeltResponse;

@RequestMapping("/smelts")
@RestController
public class SmeltController implements SmeltSpecification {

    @Override
    @GetMapping("/{smelt-id}")
    public ResponseEntity<SmeltResponse.Detail> getSmeltDetail(@PathVariable(name = "smelt-id") Long smeltId) {
        // TODO
        return null;
    }

    @Override
    @GetMapping("/{smelt-id}/quizzes")
    public ResponseEntity<QuizResponse.Info> getQuiz(@PathVariable(name = "smelt-id") Long smeltId) {
        return null;
    }

    @Override
    @PatchMapping("/{smelt-id}/quizzes")
    public ResponseEntity<QuizResponse.SolveResult> solveQuiz(@PathVariable(name = "smelt-id") Long smeltId, @RequestBody QuizRequest.Solve request) {
        // TODO
        return null;
    }

    @Override
    @PostMapping("/{smelt-id}/comments")
    public ResponseEntity<SmeltResponse.Detail> registerCommentTo(
            @PathVariable(name = "smelt-id") Long smeltId,
            @RequestBody SmeltRequest.RegisterComment request) {
        // TODO
        return null;
    }

    @Override
    @GetMapping(value = "/types")
    public ResponseEntity<SmeltResponse.AllOfType> getSmeltTypes() {
        // TODO
        return null;
    }
}
