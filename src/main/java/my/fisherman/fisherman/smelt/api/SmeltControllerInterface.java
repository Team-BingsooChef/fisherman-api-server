package my.fisherman.fisherman.smelt.api;

import org.springframework.http.ResponseEntity;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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
}
