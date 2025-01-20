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
import my.fisherman.fisherman.smelt.api.response.SmeltResponse;
import my.fisherman.fisherman.smelt.api.response.SmeltTypeResponse;


@RestController
public class SmeltController implements SmeltSpecification {

    @Override
    @GetMapping(value = "/smelt/types")
    public ResponseEntity<SmeltTypeResponse.All> getSmeltTypes() {
        // TODO
        return null;
    }

    @Override
    @PostMapping(value = "/users/smelts")
    public ResponseEntity<SmeltResponse.Simple> createRandomSmelt() {
        // TODO
        return null;
    }

    @Override
    @GetMapping(value = "/users/smelts/types")
    public ResponseEntity<SmeltTypeResponse.Count> getMySmeltTypes() {
        // TODO
        return null;
    }

    @Override
    @GetMapping(value = "/users/sent-smelts")
    public ResponseEntity<SmeltResponse.Page> getSentSmelts(@PageableDefault(page = 0, size = 8) Pageable pageable) {
        // TODO
        return null;
    }

    @Override
    @PostMapping(value = "/fishing-spots/{fishing-spot-id}/smelts")
    public ResponseEntity<SmeltResponse.Detail> sendSmelt(
        @PathVariable(name = "fishing-spot-id") Long fishingSpotId,
        @RequestBody SendSmeltRequest.Letter request) {
        // TODO
        return null;
    }

    @Override
    @GetMapping("/fishing-spots/{fishing-spot-id}")
    public ResponseEntity<FishingSpotResponse.Page> getFishingSpot(@
        PageableDefault(page = 0, size = 8) Pageable pageable,
        @PathVariable(name = "fishing-spot-id") Long id) {
        // TODO
        return null;
    }

    @Override
    @GetMapping("/smelts/{smelt-id}")
    public ResponseEntity<SmeltResponse.Detail> getSmeltDetail(@PathVariable(name = "smelt-id") Long smeltId) {
        // TODO
        return null;
    }
}
