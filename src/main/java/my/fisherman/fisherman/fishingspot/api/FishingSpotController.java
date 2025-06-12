package my.fisherman.fisherman.fishingspot.api;

import java.util.List;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import my.fisherman.fisherman.fishingspot.api.request.FishingSpotRequest;
import my.fisherman.fisherman.fishingspot.api.response.FishingSpotResponse;
import my.fisherman.fisherman.fishingspot.api.response.FishingSpotResponse.FishingSpot;
import my.fisherman.fisherman.fishingspot.application.FishingSpotService;
import my.fisherman.fisherman.fishingspot.application.command.FishingSpotCommand;
import my.fisherman.fisherman.fishingspot.application.dto.FishingSpotInfo;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/fishing-spots")
public class FishingSpotController implements FishingSpotSpecification {

    private final FishingSpotService fishingSpotService;

    @Override
    @GetMapping("/mine")
    public ResponseEntity<FishingSpotResponse.FishingSpot> getMine() {
        FishingSpotInfo.Simple info = fishingSpotService.getMyFishingSpot();

        return ResponseEntity.ok().body(FishingSpotResponse.FishingSpot.from(info));
    }

    @Override
    @PostMapping("/{fishing-spot-id}/smelts")
    public ResponseEntity<FishingSpotResponse.ReceivedSmelt> sendSmelt(
        @PathVariable(name = "fishing-spot-id") Long fishingSpotId,
        @Valid @RequestBody FishingSpotRequest.Send request
    ) {
        FishingSpotCommand.SendSmelt command = request.toCommand(fishingSpotId);
        FishingSpotInfo.DetailSmelt info = fishingSpotService.sendSmeltTo(command);

        return ResponseEntity.ok().body(FishingSpotResponse.ReceivedSmelt.from(info));
    }

    @Override
    @GetMapping("/{fishing-spot-id}/smelts")
    public ResponseEntity<FishingSpotResponse.Page> getSmeltsOf(
        @PathVariable(name = "fishing-spot-id") Long fishingSpotId,
        @PageableDefault(page = 0, size = 8) Pageable pageable
    ) {
        FishingSpotInfo.SmeltPage info = fishingSpotService.getSmelts(fishingSpotId, pageable);

        return ResponseEntity.ok().body(FishingSpotResponse.Page.from(info));
    }

    @Override
    @GetMapping()
    public ResponseEntity<List<FishingSpot>> searchFishingSpot(
        @RequestParam(value = "keyword") String nickname
    ) {
        List<FishingSpotInfo.Simple> fishingSpots = fishingSpotService.searchFishingSpot(nickname);

        List<FishingSpot> responses = fishingSpots.stream()
            .map(FishingSpot::from)
            .toList();

        return ResponseEntity.ok(responses);
    }

    @Override
    @PatchMapping("/{fishing-spot-id}/public")
    public ResponseEntity<Void> updatePublic(
        @PathVariable("fishing-spot-id") Long fishingSpotId,
        @Valid @RequestBody FishingSpotRequest.UpdatePublic request
    ) {
        FishingSpotCommand.UpdatePublic command = request.toCommand(fishingSpotId);
        fishingSpotService.updatePublic(command);

        return ResponseEntity.ok().build();
    }
}
