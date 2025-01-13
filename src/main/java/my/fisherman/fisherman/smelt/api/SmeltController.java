package my.fisherman.fisherman.smelt.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import my.fisherman.fisherman.smelt.api.response.SmeltTypeResponse;

@RestController
public class SmeltController implements SmeltControllerInterface {

    @Override
    @GetMapping(value = "/smelt/types")
    public ResponseEntity<SmeltTypeResponse> getSmeltTypes() {
        // TODO
        return null;
    }
}
