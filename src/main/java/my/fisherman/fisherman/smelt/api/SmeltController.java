package my.fisherman.fisherman.smelt.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import my.fisherman.fisherman.smelt.api.request.SendSmeltRequest;
import my.fisherman.fisherman.smelt.api.response.SendSmeltResponse;
import my.fisherman.fisherman.smelt.api.response.SmeltTypeCountResponse;
import my.fisherman.fisherman.smelt.api.response.SmeltTypeResponse;

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
    public ResponseEntity<SmeltTypeCountResponse> getSentSmelts() {
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
}
