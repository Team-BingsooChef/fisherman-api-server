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
public interface QuizSpecification {


    @Operation(
        summary = "퀴즈 조회 API",
        description = "지정한 퀴즈를 조회합니다.<br>Question의 isAnswer은 푼 경우에만 값이 존재하고, 풀기 전인 경우 null이 반환됩니다.<br>" + "퀴즈 권한: 받거나 보낸 빙어인 경우",
        responses = {
            @ApiResponse(
                responseCode = "200", content = @Content(schema = @Schema(implementation = QuizResponse.Quiz.class), mediaType = "application/json")
            ),
            @ApiResponse(responseCode = "403", description = "S302 - 빙어에 권한이 없습니다.", content = @Content()),
            @ApiResponse(responseCode = "404", description = "S402 - 존재하지 않는 빙어입니다.", content = @Content())
        }
    )
    ResponseEntity<QuizResponse.Quiz> getQuiz(Long smeltId);

    @Operation(
        summary = "퀴즈 풀기 API",
        description = "지정한 퀴즈의 답을 제출합니다. <br>" + "퀴즈 권한: 받은 빙어인 경우",
        responses = {
            @ApiResponse(
                responseCode = "200", content = @Content(schema = @Schema(implementation = SolveQuizResponse.Result.class), mediaType = "application/json")
            ),
            @ApiResponse(responseCode = "400", description = "S005 - 이미 해결된 퀴즈입니다."),
            @ApiResponse(responseCode = "403", description = "S302 - 퀴즈 풀이에 권한이 없습니다.", content = @Content()),
        }
    )
    ResponseEntity<SolveQuizResponse.Result> solveQuiz(@RequestBody SolveQuizRequest.Try request);
}
