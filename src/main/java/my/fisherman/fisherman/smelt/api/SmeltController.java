package my.fisherman.fisherman.smelt.api;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import my.fisherman.fisherman.smelt.api.request.SendSmeltRequest;
import my.fisherman.fisherman.smelt.api.response.FishingSpotResponse;
import my.fisherman.fisherman.smelt.api.response.SendSmeltResponse;
import my.fisherman.fisherman.smelt.api.response.SmeltDetailResponse;
import my.fisherman.fisherman.smelt.api.response.SmeltPageResponse;
import my.fisherman.fisherman.smelt.api.response.SmeltTypeCountResponse;
import my.fisherman.fisherman.smelt.api.response.SmeltTypeResponse;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
public class SmeltController implements SmeltControllerInterface {

    @Override
    @GetMapping(value = "/smelt/types")
    public ResponseEntity<SmeltTypeResponse> getSmeltTypes() {
        // TODO
        return null;
    }

    @Override
    @GetMapping(value = "/users/smelt/types")
    public ResponseEntity<SmeltTypeCountResponse> getMySmeltTypes() {
        // TODO
        return null;
    }

    @Override
    @GetMapping(value = "/users/sent-smelt")
    public ResponseEntity<SmeltPageResponse> getSentSmelts(@PageableDefault(page = 0, size = 8) Pageable pageable) {
        // TODO
        return null;
    }

    @Override
    @PostMapping(value = "/fishing-spot/{fishing-spot-id}/smelts")
    public ResponseEntity<SendSmeltResponse> sendSmelt(
        @PathVariable Long Id,
        @RequestBody SendSmeltRequest request) {
        // TODO
        return null;
    }

    @Override
    @GetMapping("/fishing-spot/{id}")
    public ResponseEntity<FishingSpotResponse> getFishingSpot(@PageableDefault(page = 0, size = 8) Pageable pageable, @PathVariable Long id) {
        // TODO
        return null;
    }

    @Override
    @GetMapping("/smelts/{id}")
    public ResponseEntity<SmeltDetailResponse> getSmeldDetail(@PathVariable Long id) {
        // TODO
        return null;
    }
}
