package my.fisherman.fisherman.quiz.api;

import org.springframework.http.ResponseEntity;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import my.fisherman.fisherman.quiz.api.request.SolveQuizRequest;
import my.fisherman.fisherman.quiz.api.response.SolveQuizResponse;

@Tag(name = "Quiz")
public interface QuizControllerInterface {


    @Operation(
        summary = "퀴즈 풀기 API",
        description = "지정한 빙어의 퀴즈의 답을 제출합니다. <br>" + "자신이 받은, 아직 풀지 않은 퀴즈만 풀 수 있습니다."
    )
    @ApiResponse(
        responseCode = "200", content = @Content(schema = @Schema(implementation = SolveQuizResponse.class), mediaType = "application/json")
    )
    ResponseEntity<SolveQuizResponse> solveQuiz(@RequestBody SolveQuizRequest request);
}
