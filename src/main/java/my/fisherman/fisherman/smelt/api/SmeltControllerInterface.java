package my.fisherman.fisherman.smelt.api;

import org.springframework.http.ResponseEntity;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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
}
