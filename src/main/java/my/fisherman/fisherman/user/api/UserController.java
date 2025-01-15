package my.fisherman.fisherman.user.api;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import my.fisherman.fisherman.user.api.dto.UserRequest;
import my.fisherman.fisherman.user.application.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController implements UserSpecification {
    private final UserService userService;

    @Override
    @PostMapping("/sign-up")
    public ResponseEntity<Void> signUp(@Valid @RequestBody UserRequest.Create request) {
        var command = request.toCommand();
        userService.signUp(command);
        return ResponseEntity.ok().build();
    }
}
