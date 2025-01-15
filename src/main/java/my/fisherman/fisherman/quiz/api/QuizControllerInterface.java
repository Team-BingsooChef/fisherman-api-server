package my.fisherman.fisherman.quiz.api;

import org.springframework.http.ResponseEntity;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import my.fisherman.fisherman.quiz.api.request.SolveQuizRequest;
import my.fisherman.fisherman.quiz.api.response.QuizResponse;
import my.fisherman.fisherman.quiz.api.response.SolveQuizResponse;

@Tag(name = "Quiz")
public interface QuizControllerInterface {


    @Operation(
        summary = "퀴즈 조회 API",
        description = "지정한 빙어의 퀴즈를 조회합니다. <br>" + "권한이 없는 경우(받거나 보낸 빙어가 아님), 조회할 수 없습니다.<br>" + "Question의 isAnswer은 푼 경우에만 값이 존재하고, 풀기 전인 경우 null이 반환됩니다."
    )
    @ApiResponse(
        responseCode = "200", content = @Content(schema = @Schema(implementation = QuizResponse.class), mediaType = "application/json")
    )
    ResponseEntity<QuizResponse> solveQuiz(Long smeltId);

    @Operation(
        summary = "퀴즈 풀기 API",
        description = "지정한 퀴즈의 답을 제출합니다. <br>" + "자신이 받은, 아직 풀지 않은 퀴즈만 풀 수 있습니다."
    )
    @ApiResponse(
        responseCode = "200", content = @Content(schema = @Schema(implementation = SolveQuizResponse.class), mediaType = "application/json")
    )
    ResponseEntity<SolveQuizResponse> solveQuiz(@RequestBody SolveQuizRequest request);
}
