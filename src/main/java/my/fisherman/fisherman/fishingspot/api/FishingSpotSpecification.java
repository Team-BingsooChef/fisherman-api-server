package my.fisherman.fisherman.fishingspot.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import my.fisherman.fisherman.fishingspot.api.request.FishingSpotRequest;
import my.fisherman.fisherman.fishingspot.api.response.FishingSpotResponse;
import my.fisherman.fisherman.fishingspot.api.response.FishingSpotResponse.FishingSpot;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

@Tag(name = "낚시터")
public interface FishingSpotSpecification {

    @Operation(
        summary = "내 낚시터 조회 API",
        description = "현재 사용자의 낚시터 정보를 반환합니다.<br>" + "Access token이 필요합니다.<br>",
        responses = {
            @ApiResponse(
                responseCode = "200", content = @Content(schema = @Schema(implementation = FishingSpotResponse.FishingSpot.class), mediaType = "application/json")
            )
        }
    )
    ResponseEntity<FishingSpotResponse.FishingSpot> getMine();

    @Operation(
        summary = "낚시터에 빙어 보내기 API",
        description = "주어진 빙어를 지정한 낚시터에 보냅니다.<br>" + "Access token이 필요합니다.<br>" + "권한: 사용자가 뽑은 빙어",
        responses = {
            @ApiResponse(
                responseCode = "200", content = @Content(schema = @Schema(implementation = FishingSpotResponse.ReceivedSmelt.class), mediaType = "application/json")
            ),
            @ApiResponse(responseCode = "403", description = "F301 - 빙어에 권한이 없습니다.", content = @Content()),
            @ApiResponse(responseCode = "404", description = "F401 - 존재하지 않는 낚시터입니다.", content = @Content()),
            @ApiResponse(responseCode = "404", description = "F402 - 존재하지 않는 빙어입니다.", content = @Content())
        }
    )
    ResponseEntity<FishingSpotResponse.ReceivedSmelt> sendSmelt(
        Long fishingSpotId,
        @RequestBody(description = "퀴즈가 없는 빙어인 경우 quiz는 null") FishingSpotRequest.Send request
    );


    @Operation(
        summary = "낚시터의 빙어 조회 API",
        description = "지정한 낚시터의 받은 빙어들을 조회합니다.<br>",
        responses = {
            @ApiResponse(
                responseCode = "200", content = @Content(schema = @Schema(implementation = FishingSpotResponse.Page.class), mediaType = "application/json")
            ),
            @ApiResponse(responseCode = "404", description = "F401 - 존재하지 않는 낚시터입니다.", content = @Content())
        }
    )
    ResponseEntity<FishingSpotResponse.Page> getSmeltsOf(Long fishingSpotId, Pageable pageable);

    @Operation(
        summary = "낚시터 검색 API",
        description = "지정한 닉네임을 포함하는 낚시터들을 검색합니다.<br>",
        responses = {
            @ApiResponse(
                responseCode = "200", content = @Content(schema = @Schema(implementation = FishingSpotResponse.FishingSpot.class), mediaType = "application/json")
            )
        }
    )
    ResponseEntity<List<FishingSpot>> searchFishingSpot(String keyword);

    @Operation(
        summary = "공개 여부 수정",
        description = "낚시터의 공개 여부를 수정합니다.",
        responses = {
            @ApiResponse(responseCode = "200", description = "공개 여부 수정 성공"),
            @ApiResponse(responseCode = "404", description = "공개 여부 수정 실패")
        }
    )
    ResponseEntity<Void> updatePublic(Long fishingSpotId, FishingSpotRequest.UpdatePublic request);
}
