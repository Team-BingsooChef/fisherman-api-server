package my.fisherman.fisherman.fishingspot.api;

import my.fisherman.fisherman.fishingspot.api.request.FishingSpotRequest;
import my.fisherman.fisherman.fishingspot.api.response.FishingSpotResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/fishing-spots")
@RestController
public class FishingSpotController implements FishingSpotSpecification {

    @Override
    @PostMapping("/{fishing-spot-id}/smelts")
    public ResponseEntity<FishingSpotResponse.ReceivedSmelt> sendSmelt(
            @PathVariable(name = "fishing-spot-id") Long fishingSpotId,
            @RequestBody FishingSpotRequest.Send request) {
        return null;
    }

    @Override
    @GetMapping("/{fishing-spot-id}/smelts")
    public ResponseEntity<FishingSpotResponse.Page> getSmeltsOf(@PathVariable(name = "fishing-spot-id") Long fishingSpotId) {
        // TODO
        return null;
    }
}
