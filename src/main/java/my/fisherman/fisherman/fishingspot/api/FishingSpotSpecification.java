package my.fisherman.fisherman.fishingspot.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import my.fisherman.fisherman.fishingspot.api.request.FishingSpotRequest;
import my.fisherman.fisherman.fishingspot.api.response.FishingSpotResponse;
import org.springframework.http.ResponseEntity;

@Tag(name = "낚시터")
public interface FishingSpotSpecification {
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
    ResponseEntity<FishingSpotResponse.ReceivedSmelt> sendSmelt(Long fishingSpotId, @RequestBody FishingSpotRequest.Send request);


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
    ResponseEntity<FishingSpotResponse.Page> getSmeltsOf(Long fishingSpotId);

}
