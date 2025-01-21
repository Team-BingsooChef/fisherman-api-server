package my.fisherman.fisherman.smelt.api;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import my.fisherman.fisherman.smelt.api.request.CommentRequest;
import my.fisherman.fisherman.smelt.api.request.SmeltRequest;
import my.fisherman.fisherman.smelt.api.response.SmeltPageResponse;
import my.fisherman.fisherman.smelt.api.response.SmeltResponse;
import my.fisherman.fisherman.smelt.api.response.SmeltTypeResponse;


@RestController
public class SmeltController implements SmeltSpecification {

    @Override
    @GetMapping(value = "/smelts/types")
    public ResponseEntity<SmeltTypeResponse.All> getSmeltTypes() {
        // TODO
        return null;
    }

    @Override
    @PostMapping(value = "/smelts")
    public ResponseEntity<SmeltResponse.Simple> drawSmelt() {
        // TODO
        return null;
    }

    @Override
    @GetMapping(value = "/users/{user-id}/smelts/types")
    public ResponseEntity<SmeltTypeResponse.Count> getSmeltTypesOfUser(@PathVariable(name = "user-id") Long userId) {
        // TODO
        return null;
    }

    @Override
    @GetMapping(value = "/users/{user-id}/smelts/sent")
    public ResponseEntity<SmeltResponse.Detail> getSentSmelts(@PageableDefault(page = 0, size = 8) Pageable pageable) {
        // TODO
        return null;
    }

    @Override
    @PatchMapping(value = "/smelts/{smelt-id}")
    public ResponseEntity<SmeltResponse.Detail> sendSmelt(
        @PathVariable(name = "smelt-id") Long smeltId,
        @RequestBody SmeltRequest.Send request) {
        // TODO
        return null;
    }

    @Override
    @GetMapping("/users/{user-id}/smelts")
    public ResponseEntity<SmeltPageResponse.Simple> getReceivedSmelts(@
        PageableDefault(page = 0, size = 8) Pageable pageable,
        @PathVariable(name = "user-id") Long userId) {
        // TODO
        return null;
    }

    @Override
    @GetMapping("/smelts/{smelt-id}")
    public ResponseEntity<SmeltResponse.Detail> getSmeltDetail(@PathVariable(name = "smelt-id") Long smeltId) {
        // TODO
        return null;
    }

    @Override
    @PostMapping("/smelts/{smelt-id}/comments")
    public ResponseEntity<SmeltResponse.Detail> commentSmelt(
        @PathVariable(name = "smelt-id") Long smeltId,
        @RequestBody CommentRequest.Comment request) {
        // TODO
        return null;
    }
}
