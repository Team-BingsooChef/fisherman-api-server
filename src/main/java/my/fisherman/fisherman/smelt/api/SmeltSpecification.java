package my.fisherman.fisherman.smelt.api;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import my.fisherman.fisherman.smelt.api.request.CommentRequest;
import my.fisherman.fisherman.smelt.api.request.SmeltRequest;
import my.fisherman.fisherman.smelt.api.response.SmeltPageResponse;
import my.fisherman.fisherman.smelt.api.response.SmeltResponse;
import my.fisherman.fisherman.smelt.api.response.SmeltTypeResponse;

@Tag(name = "Smelt")
public interface SmeltSpecification {

    @Operation(
        summary = "전체 빙어 종류 조회 API",
        description = "전체 빙어 종류를 반환합니다.",
        responses = {
                @ApiResponse(
                        responseCode = "200", content = @Content(schema = @Schema(implementation = SmeltTypeResponse.All.class), mediaType = "application/json")
                ),
        }
    )
    ResponseEntity<SmeltTypeResponse.All> getSmeltTypes();

    @Operation(
        summary = "빙어 뽑기 API",
        description = "Access token 내 user id를 가진 사용자에게 지정된 확률로 새로운 빙어를 생성합니다.",
        responses = {
            @ApiResponse(
                responseCode = "201", content = @Content(schema = @Schema(implementation = SmeltResponse.Simple.class), mediaType = "application/json")
            ),
            @ApiResponse(responseCode = "400", description = "S001 - 사용자의 코인이 부족합니다.", content = @Content())
        }
    )
    ResponseEntity<SmeltResponse.Simple> drawSmelt();
    

    @Operation(
        summary = "사용자의 뽑은 빙어 종류 조회 API",
        description = "주어진 사용자가 뽑은 빙어의 종류와 종류 별 개수를 반환합니다.<br>다른 사용자에게 보낸 빙어는 제외됩니다.<br>권한: 사용자 본인",
        responses = {
            @ApiResponse(
                responseCode = "200", content = @Content(schema = @Schema(implementation = SmeltTypeResponse.Count.class), mediaType = "application/json")
            )
        }
    )
    ResponseEntity<SmeltTypeResponse.Count> getSmeltTypesOfUser(Long userId);


    @Operation(
        summary = "빙어 보내기 API",
        description = "주어진 빙어를 다른 사용자에게게 보냅니다.<br>권한: 사용자가 뽑은 빙어",
        responses = {
            @ApiResponse(
                    responseCode = "200", content = @Content(schema = @Schema(implementation = SmeltResponse.Detail.class), mediaType = "application/json")
            ),
            @ApiResponse(responseCode = "404", description = "S401 - 존재하지 않는 낚시터입니다. <br> S402 -존재하지 않는 빙어입니다.", content = @Content()),
        }
    )
    ResponseEntity<SmeltResponse.Detail> sendSmelt(Long smeltId, @RequestBody(description = "빙어와 함께 보낼 편지") SmeltRequest.Send request);


    @Operation(
        summary = "보낸 빙어 조회 API",
        description = "주어진 사용자가 타 사용자에게 보낸 빙어의 배열을 반환합니다.<br>" + "이때 배열은 페이지네이션이 적용되어 있습니다.<br>권한: 사용자 본인",
        responses = {
            @ApiResponse(
                responseCode = "200", content = @Content(schema = @Schema(implementation = SmeltResponse.Detail.class), mediaType = "application/json")
            )
        }
    )
    ResponseEntity<SmeltResponse.Detail> getSentSmelts(@ParameterObject Pageable pageable);


    @Operation(
        summary = "받은 빙어 조회 API",
        description = "지정한 사용자가 받은 빙어의 배열을 조회합니다. <br>"
            + "이때 배열은 페이지네이션이 적용됩니다.<br>"
            + "아직 낚시터 주인이 읽지 않은 빙어는 smelt type이 unknown으로 반환됩니다.",
        responses = {
            @ApiResponse(
                responseCode = "200", content = @Content(schema = @Schema(implementation = SmeltPageResponse.Simple.class), mediaType = "application/json")
            ),
            @ApiResponse(responseCode = "404", description = "S401 - 존재하지 않는 낚시터입니다.", content = @Content())
        }
    )
    ResponseEntity<SmeltPageResponse.Simple> getReceivedSmelts(@ParameterObject Pageable pageable, Long userId);

    @Operation(
        summary = "빙어 상세 조회 API",
        description = "지정한 빙어의 자세한 정보를 조회합니다. <br>" + "권한: 보낸 빙어거나, 받은 빙어고 퀴즈가 있다면 푼 경우.",
        responses = {
            @ApiResponse(
                responseCode = "200", content = @Content(schema = @Schema(implementation = SmeltResponse.Detail.class), mediaType = "application/json")
            ),
            @ApiResponse(responseCode = "404", description = "S402 - 존재하지 않는 빙어입니다.", content = @Content()),
            @ApiResponse(responseCode = "403", description = "S302 - 빙어에 대한 권한이 없습니다.", content = @Content())
        }
    )
    ResponseEntity<SmeltResponse.Detail> getSmeltDetail(Long smeltId);

    @Operation(
        summary = "빙어에 코멘트 작성 API",
        description = "지정한 빙어에 코멘트를 작성합니다. <br>" + "권한: 받은 빙어고 퀴즈가 있다면 풀었음.",
        responses = {
            @ApiResponse(
                responseCode = "200", content = @Content(schema = @Schema(implementation = SmeltResponse.Detail.class), mediaType = "application/json")
            ),
            @ApiResponse(responseCode = "404", description = "S402 - 존재하지 않는 빙어입니다.", content = @Content()),
            @ApiResponse(responseCode = "403", description = "S302 - 빙어에 대한 권한이 없습니다.", content = @Content())
        }
    )
    ResponseEntity<SmeltResponse.Detail> commentSmelt(Long smeltId, @RequestBody CommentRequest.Comment request);
}
