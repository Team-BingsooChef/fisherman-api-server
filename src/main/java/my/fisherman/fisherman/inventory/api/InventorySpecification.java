package my.fisherman.fisherman.inventory.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import my.fisherman.fisherman.inventory.api.response.InventoryResponse;
import org.springframework.http.ResponseEntity;

@Tag(name = "인벤토리 (뽑은 빙어)")
public interface InventorySpecification {

    @Operation(
            summary = "빙어 뽑기 API",
            description = "지정한 인벤토리에 지정된 확률로 새로운 빙어를 생성합니다.<br>" + "권한: 사용자의 인벤토리",
            responses = {
                    @ApiResponse(
                            responseCode = "201", content = @Content(schema = @Schema(implementation = InventoryResponse.DrewSmelt.class), mediaType = "application/json")
                    ),
                    @ApiResponse(responseCode = "400", description = "I001 - 코인이 부족합니다.", content = @Content()),
                    @ApiResponse(responseCode = "403", description = "I301 - 인벤토리에 권한이 없습니다.", content = @Content()),
                    @ApiResponse(responseCode = "404", description = "I401 - 존재하지 않는 인벤토리입니다.", content = @Content())
            }
    )
    ResponseEntity<InventoryResponse.DrewSmelt> drawSmelt(Long inventoryId);

    @Operation(
            summary = "보낸 빙어 조회 API",
            description = "지정한 인벤토리 내 다른 낚시터에 보낸 빙어들을 조회합니다.<br>" + "이때 빙어의 목록에 페이지네이션이 적용됩니다.<br>" + "권한: 사용자의 인벤토리",
            responses = {
                    @ApiResponse(
                            responseCode = "200", content = @Content(schema = @Schema(implementation = InventoryResponse.SentSmeltPage.class), mediaType = "application/json")
                    ),
                    @ApiResponse(responseCode = "403", description = "I301 - 인벤토리에 권한이 없습니다.", content = @Content()),
                    @ApiResponse(responseCode = "404", description = "I401 - 존재하지 않는 인벤토리입니다.", content = @Content())
            }
    )
    ResponseEntity<InventoryResponse.SentSmeltPage> getSentSmelt(Long inventoryId);

    @Operation(
            summary = "뽑은 빙어 통계 조회 API",
            description = "지정한 인벤토리 내 빙어의 종류와 개수를 조회합니다.<br>" + "권한: 사용자의 인벤토리",
            responses = {
                    @ApiResponse(
                            responseCode = "200", content = @Content(schema = @Schema(implementation = InventoryResponse.Statistic.class), mediaType = "application/json")
                    ),
                    @ApiResponse(responseCode = "403", description = "I301 - 인벤토리에 권한이 없습니다.", content = @Content()),
                    @ApiResponse(responseCode = "404", description = "I401 - 존재하지 않는 인벤토리입니다.", content = @Content())
            }
    )
    ResponseEntity<InventoryResponse.Statistic> getSmeltStatistic(Long inventoryId);
}
