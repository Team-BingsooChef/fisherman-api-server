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
import my.fisherman.fisherman.smelt.api.request.SendSmeltRequest;
import my.fisherman.fisherman.smelt.api.response.FishingSpotResponse;
import my.fisherman.fisherman.smelt.api.response.SendSmeltResponse;
import my.fisherman.fisherman.smelt.api.response.SmeltDetailResponse;
import my.fisherman.fisherman.smelt.api.response.SmeltPageResponse;
import my.fisherman.fisherman.smelt.api.response.SmeltResponse;
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
        summary = "빙어 뽑기 API",
        description = "Access token 내 user id를 가진 사용자에게 지정된 확률로 새로운 빙어를 생성합니다."
    )
    @ApiResponse(
        responseCode = "201", content = @Content(schema = @Schema(implementation = SmeltResponse.Simple.class), mediaType = "application/json")
    )
    ResponseEntity<SmeltResponse.Simple> createRandomSmelt();
    

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
        description = "Access token 내 user id를 가진 사용자가 보낸 빙어의 배열을 반환합니다.<br>" + "이때 배열은 페이지네이션이 적용되어 있습니다."
    )
    @ApiResponse(
        responseCode = "200", content = @Content(schema = @Schema(implementation = SmeltPageResponse.class), mediaType = "application/json")
    )
    ResponseEntity<SmeltPageResponse> getSentSmelts(@ParameterObject Pageable pageable);


    @Operation(
        summary = "빙어 보내기 API",
        description = "내 빙어를 다른 사용자의 낚시터에 보냅니다."
    )
    @ApiResponse(
        responseCode = "200", content = @Content(schema = @Schema(implementation = SendSmeltResponse.class), mediaType = "application/json")
    )
    ResponseEntity<SendSmeltResponse> sendSmelt(Long fishingSpotId, @RequestBody(description = "빙어와 함께 보낼 편지") SendSmeltRequest request);

    @Operation(
        summary = "낚시터 조회 API",
        description = "지정한 사용자의 낚시터에 있는 빙어의 배열을 조회합니다. <br>" + "이때 배열은 페이지네이션이 적용됩니다."
    )
    @ApiResponse(
        responseCode = "200", content = @Content(schema = @Schema(implementation = FishingSpotResponse.class), mediaType = "application/json")
    )
    ResponseEntity<FishingSpotResponse> getFishingSpot(@ParameterObject Pageable pageable, Long fishingSpotId);

    @Operation(
        summary = "빙어 상세 조회 API",
        description = "지정한 빙어의 자세한 정보를 조회합니다. <br>" + "권한이 없거나 (받거나 보낸 빙어가 아님), 아직 풀지 않은 받은 빙어는 조회할 수 없습니다."
    )
    @ApiResponse(
        responseCode = "200", content = @Content(schema = @Schema(implementation = SmeltDetailResponse.class), mediaType = "application/json")
    )
    ResponseEntity<SmeltDetailResponse> getSmeldDetail(Long smeltId);
}
