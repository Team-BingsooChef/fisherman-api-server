package my.fisherman.fisherman.smelt.api;

import my.fisherman.fisherman.smelt.api.request.QuizRequest;
import my.fisherman.fisherman.smelt.api.request.SmeltRequest;
import my.fisherman.fisherman.smelt.api.response.QuizResponse;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;
import my.fisherman.fisherman.smelt.api.response.SmeltResponse;
import my.fisherman.fisherman.smelt.application.SmeltService;
import my.fisherman.fisherman.smelt.application.dto.SmeltInfo;

@RequiredArgsConstructor
@RequestMapping("/smelts")
@RestController
public class SmeltController implements SmeltSpecification {
    private final SmeltService smeltService;

    @Override
    @GetMapping("/{smelt-id}")
    public ResponseEntity<SmeltResponse.Detail> getSmeltDetail(@PathVariable(name = "smelt-id") Long smeltId) {
        SmeltInfo.Detail info = smeltService.getSmeltDetail(smeltId);

        return ResponseEntity.ok().body(SmeltResponse.Detail.from(info));
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
        SmeltInfo.Detail info = smeltService.registerComment(smeltId, request.content());

        return ResponseEntity.status(HttpStatus.CREATED).body(SmeltResponse.Detail.from(info));
    }

    @Override
    @GetMapping(value = "/types")
    public ResponseEntity<SmeltResponse.AllOfType> getSmeltTypes() {
        List<SmeltInfo.Type> info = smeltService.getSmeltTypes();

        return ResponseEntity.ok().body(SmeltResponse.AllOfType.of(info));
    }
}
