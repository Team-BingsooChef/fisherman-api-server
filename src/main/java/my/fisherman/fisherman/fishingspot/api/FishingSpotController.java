package my.fisherman.fisherman.fishingspot.api;

import java.util.List;
import lombok.RequiredArgsConstructor;
import my.fisherman.fisherman.fishingspot.api.request.FishingSpotRequest;
import my.fisherman.fisherman.fishingspot.api.response.FishingSpotResponse;
import my.fisherman.fisherman.fishingspot.api.response.FishingSpotResponse.FishingSpot;
import my.fisherman.fisherman.fishingspot.application.FishingSpotService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
    @PostMapping("/{fishing-spot-id}/smelts")
    public ResponseEntity<FishingSpotResponse.ReceivedSmelt> sendSmelt(
        @PathVariable(name = "fishing-spot-id") Long fishingSpotId,
        @RequestBody FishingSpotRequest.Send request
    ) {
        return null;
    }

    @Override
    @GetMapping("/{fishing-spot-id}/smelts")
    public ResponseEntity<FishingSpotResponse.Page> getSmeltsOf(
        @PathVariable(name = "fishing-spot-id") Long fishingSpotId
    ) {
        // TODO
        return null;
    }

    @Override
    @GetMapping()
    public ResponseEntity<List<FishingSpot>> searchFishingSpot(
        @RequestParam(value = "keyword") String nickname
    ) {
        var fishingSpots = fishingSpotService.searchFishingSpot(nickname);

        var responses = fishingSpots.stream()
            .map(FishingSpot::from)
            .toList();

        return ResponseEntity.ok(responses);
    }
}
