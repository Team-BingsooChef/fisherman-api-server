package my.fisherman.fisherman.smelt.api;

import my.fisherman.fisherman.smelt.api.request.QuizRequest;
import my.fisherman.fisherman.smelt.api.request.SmeltRequest;
import my.fisherman.fisherman.smelt.api.response.QuizResponse;
import org.springframework.http.ResponseEntity;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import my.fisherman.fisherman.smelt.api.response.SmeltResponse;

@Tag(name = "빙어")
public interface SmeltSpecification {

    @Operation(
        summary = "빙어 상세 조회 API",
        description = "지정한 빙어의 자세한 정보를 조회합니다. <br>" + "Access token이 필요합니다.<br>" + "권한: 사용자가 뽑은 빙어거나, 사용자의 낚시터에 받은 빙어고 퀴즈가 있다면 풀었음",
        responses = {
            @ApiResponse(
                responseCode = "200", content = @Content(schema = @Schema(implementation = SmeltResponse.Detail.class), mediaType = "application/json")
            ),
            @ApiResponse(responseCode = "403", description = "S301 - 빙어에 대한 권한이 없습니다.", content = @Content()),
            @ApiResponse(responseCode = "404", description = "S401 - 존재하지 않는 빙어입니다.", content = @Content())
        }
    )
    ResponseEntity<SmeltResponse.Detail> getSmeltDetail(Long smeltId);


    @Operation(
            summary = "빙어의 퀴즈 조회 API",
            description = "지정한 빙어의 퀴즈를 조회합니다.<br>Question의 isAnswer은 푼 경우에만 값이 존재하고, 풀기 전인 경우 null이 반환됩니다.<br>" + "Access token이 필요합니다.<br>" + "권한: 사용자가 뽑거나 사용자의 낚시터에 받은 빙어",
            responses = {
                    @ApiResponse(
                            responseCode = "200", content = @Content(schema = @Schema(implementation = QuizResponse.Detail.class), mediaType = "application/json")
                    ),
                    @ApiResponse(responseCode = "403", description = "S301 - 빙어에 권한이 없습니다.", content = @Content()),
                    @ApiResponse(responseCode = "404", description = "S401 - 존재하지 않는 빙어입니다.", content = @Content())
            }
    )
    ResponseEntity<QuizResponse.Detail> getQuiz(Long smeltId);

    @Operation(
            summary = "빙어의 퀴즈 풀기 API",
            description = "지정한 빙어의 퀴즈의 답을 제출합니다. <br>" + "Access token이 필요합니다.<br>" + "권한: 사용자의 낚시터에 받은 빙어",
            responses = {
                    @ApiResponse(
                            responseCode = "200", content = @Content(schema = @Schema(implementation = QuizResponse.SolveResult.class), mediaType = "application/json")
                    ),
                    @ApiResponse(responseCode = "400", description = "S005 - 이미 해결된 퀴즈입니다.", content = @Content()),
                    @ApiResponse(responseCode = "403", description = "S302 - 퀴즈에 대한 권한이 없습니다.", content = @Content()),
            }
    )
    ResponseEntity<QuizResponse.SolveResult> solveQuiz(Long smeltId, @RequestBody QuizRequest.Solve request);


    @Operation(
            summary = "빙어에 코멘트 작성 API",
            description = "지정한 빙어에 코멘트를 작성합니다. <br>" + "Access token이 필요합니다.<br>" + "권한: 사용자의 낚시터에 받은 빙어고 퀴즈가 있다면 풀었음",
            responses = {
                    @ApiResponse(
                            responseCode = "200", content = @Content(schema = @Schema(implementation = SmeltResponse.Detail.class), mediaType = "application/json")
                    ),
                    @ApiResponse(responseCode = "403", description = "S301 - 빙어에 대한 권한이 없습니다.", content = @Content()),
                    @ApiResponse(responseCode = "404", description = "S401 - 존재하지 않는 빙어입니다.", content = @Content())
            }
    )
    ResponseEntity<SmeltResponse.Detail> registerCommentTo(Long smeltId, @RequestBody SmeltRequest.RegisterComment request);


    @Operation(
            summary = "전체 빙어 종류 조회 API",
            description = "전체 빙어 종류를 반환합니다.",
            responses = {
                    @ApiResponse(
                            responseCode = "200", content = @Content(schema = @Schema(implementation = SmeltResponse.AllOfType.class), mediaType = "application/json")
                    ),
            }
    )
    ResponseEntity<SmeltResponse.AllOfType> getSmeltTypes();
}
