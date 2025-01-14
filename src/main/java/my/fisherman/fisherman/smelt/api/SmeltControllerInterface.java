package my.fisherman.fisherman.smelt.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import my.fisherman.fisherman.smelt.api.request.SendSmeltRequest;
import my.fisherman.fisherman.smelt.api.response.SendSmeltResponse;
import my.fisherman.fisherman.smelt.api.response.SmeltPageResponse;
import my.fisherman.fisherman.smelt.api.response.SmeltTypeCountResponse;
import my.fisherman.fisherman.smelt.api.response.SmeltTypeResponse;

@Tag(name = "Smelt")
public interface SmeltControllerInterface {

    @Operation(
        summary = "빙어 종류 조회 API",
        description = "전체 빙어 종류를 반환합니다."
    )
    @ApiResponse(
        responseCode = "200", content = @Content(schema = @Schema(implementation = SmeltTypeResponse.class), mediaType = "application/json")
    )
    ResponseEntity<SmeltTypeResponse> getSmeltTypes();
    

    @Operation(
        summary = "내 빙어 종류 조회 API",
        description = "Access token 내 user id를 가진 사용자가 가지고 있는 빙어의 종류와 종류 별 개수를 반환합니다.<br>" + "다른 사용자에게 보낸 빙어는 제외됩니다."
    )
    @ApiResponse(
        responseCode = "200", content = @Content(schema = @Schema(implementation = SmeltTypeCountResponse.class), mediaType = "application/json")
    )
    ResponseEntity<SmeltTypeCountResponse> getMySmeltTypes();


    @Operation(
        summary = "내가 보낸 빙어 조회 API",
        description = "Access token 내 user id를 가진 사용자가 보낸 빙어의 배열을 반환합니다.<br>" + "페이지네이션 적용: ."
    )
    @ApiResponse(
        responseCode = "200", content = @Content(schema = @Schema(implementation = SmeltPageResponse.class), mediaType = "application/json")
    )
    ResponseEntity<SmeltTypeCountResponse> getSentSmelts();


    @Operation(
        summary = "빙어 보내기 API",
        description = "내 빙어를 다른 사용자의 낚시터에 보냅니다."
    )
    @ApiResponse(
        responseCode = "200", content = @Content(schema = @Schema(implementation = SendSmeltResponse.class), mediaType = "application/json")
    )
    ResponseEntity<SendSmeltResponse> sendSmelt(@PathVariable Long fishingSpotId, @RequestBody(description = "빙어와 함께 보낼 편지") SendSmeltRequest request);
}
