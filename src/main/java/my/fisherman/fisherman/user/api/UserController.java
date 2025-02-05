package my.fisherman.fisherman.user.api;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import my.fisherman.fisherman.user.api.dto.UserRequest;
import my.fisherman.fisherman.user.api.dto.UserResponse.Coin;
import my.fisherman.fisherman.user.api.dto.UserResponse.Info;
import my.fisherman.fisherman.user.application.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController implements UserSpecification {

    private final UserService userService;

    @Override
    @PostMapping("/sign-up")
    public ResponseEntity<Void> signUp(@Valid @RequestBody UserRequest.Create request) {
        var command = request.toCommand();
        userService.signUp(command);
        return ResponseEntity.ok().build();
    }

    @Override
    @GetMapping("/{userId}")
    public ResponseEntity<Info> getMyInfo(@PathVariable Long userId) {
        var userInfo = userService.getMyInfo(userId);

        return ResponseEntity.ok(Info.from(userInfo));

    }

    @Override
    @GetMapping("/{userId}/coin")
    public ResponseEntity<Coin> getMyCoin(@PathVariable Long userId) {
        var userInfo = userService.getMyInfo(userId);

        return ResponseEntity.ok(Coin.from(userInfo));
    }
}
