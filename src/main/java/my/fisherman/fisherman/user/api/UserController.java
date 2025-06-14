package my.fisherman.fisherman.user.api;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import my.fisherman.fisherman.user.api.dto.UserRequest;
import my.fisherman.fisherman.user.api.dto.UserResponse.HealthCheck;
import my.fisherman.fisherman.user.api.dto.UserResponse.Info;
import my.fisherman.fisherman.user.application.UserService;
import my.fisherman.fisherman.user.application.command.UserCommand;
import my.fisherman.fisherman.user.application.dto.UserInfo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
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
        UserCommand.SignUp command = request.toCommand();
        userService.signUp(command);
        return ResponseEntity.ok().build();
    }

    @Override
    @GetMapping("/{userId}")
    public ResponseEntity<Info> getMyInfo(@PathVariable Long userId) {
        UserInfo.Simple userInfo = userService.getMyInfo(userId);

        return ResponseEntity.ok(Info.from(userInfo));

    }

    @Override
    @PatchMapping("/{user-id}/nickname")
    public ResponseEntity<Void> updateNickname(
        @PathVariable(value = "user-id") Long userId,
        @Valid @RequestBody UserRequest.UpdateNickname request
    ) {
        UserCommand.UpdateNickname command = request.toCommand();
        userService.updateNickname(userId, command);
        return ResponseEntity.ok().build();
    }

    @Override
    @PatchMapping("/{user-id}/password")
    public ResponseEntity<Void> updatePassword(
        @PathVariable(value = "user-id") Long userId,
        @Valid @RequestBody UserRequest.UpdatePassword request
    ) {
        UserCommand.UpdatePassword command = request.toCommand();
        userService.updatePassword(userId, command);

        return ResponseEntity.ok().build();
    }

    @Override
    @GetMapping("/health_check")
    public ResponseEntity<HealthCheck> healthCheck() {
        UserInfo.Detail userInfo = userService.getMyDetailInfo();

        return ResponseEntity.ok(HealthCheck.from(userInfo));
    }
}
