package my.fisherman.fisherman.user.application;

import lombok.RequiredArgsConstructor;
import my.fisherman.fisherman.auth.repository.AuthenticationRepository;
import my.fisherman.fisherman.fishingspot.domain.FishingSpot;
import my.fisherman.fisherman.fishingspot.repository.FishingSpotRepository;
import my.fisherman.fisherman.inventory.domain.Inventory;
import my.fisherman.fisherman.inventory.repository.InventoryRepository;
import my.fisherman.fisherman.security.util.SecurityUtil;
import my.fisherman.fisherman.user.application.command.UserCommand;
import my.fisherman.fisherman.user.application.command.UserCommand.UpdatePassword;
import my.fisherman.fisherman.user.application.dto.UserInfo;
import my.fisherman.fisherman.user.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final AuthenticationRepository authenticationRepository;
    private final InventoryRepository inventoryRepository;
    private final FishingSpotRepository fishingSpotRepository;

    @Transactional
    public void signUp(UserCommand.SignUp command) {
        var email = command.email();

        //TODO: 예외 세분화 필요
        var authResult = authenticationRepository.find(email)
            .orElseThrow();
        if (authResult.notVerified()) {
            throw new IllegalArgumentException("이메일 인증이 필요합니다.");
        }

        userRepository.findUserByEmailWithWriteLock(email)
            .ifPresent(user -> {
                throw new IllegalArgumentException("이미 가입된 이메일입니다.");
            });

        var encodedPassword = passwordEncoder.encode(command.password());
        var user = command.toEntity(encodedPassword);
        userRepository.save(user);

        var inventory = Inventory.of(user);
        var fishingSpot = FishingSpot.of(user);
        inventoryRepository.save(inventory);
        fishingSpotRepository.save(fishingSpot);
    }

    @Transactional(readOnly = true)
    public UserInfo.Simple getMyInfo(Long userId) {
        var currentUserId = SecurityUtil.getCurrentUserId()
            .orElseThrow(() -> new IllegalArgumentException("로그인이 필요합니다."));

        if (currentUserId.equals(userId)) {
            var user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("회원 정보를 찾을 수 없습니다."));
            return UserInfo.Simple.from(user);
        }

        throw new IllegalArgumentException("본인의 정보만 조회 가능합니다.");
    }

    @Transactional
    public void updateNickname(Long userId, UserCommand.UpdateNickname command) {
        var currentUserId = SecurityUtil.getCurrentUserId()
            .orElseThrow(() -> new IllegalArgumentException("로그인이 필요합니다."));
        if (currentUserId.equals(userId)) {
            var user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("회원 정보를 찾을 수 없습니다."));
            user.updateNickname(command.nickname());
        }

        throw new IllegalArgumentException("본인의 정보만 수정 가능합니다.");
    }

    @Transactional
    public void updatePublic(Long userId, UserCommand.UpdatePublic command) {
        var currentUserId = SecurityUtil.getCurrentUserId()
            .orElseThrow(() -> new IllegalArgumentException("로그인이 필요합니다."));
        if (currentUserId.equals(userId)) {
            var user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("회원 정보를 찾을 수 없습니다."));
            user.updatePublic(command.isPublic());
        }

        throw new IllegalArgumentException("본인의 정보만 수정 가능합니다.");
    }

    public void updatePassword(Long userId, UpdatePassword command) {
        var currentUserId = SecurityUtil.getCurrentUserId()
            .orElseThrow(() -> new IllegalArgumentException("로그인이 필요합니다."));
        if (currentUserId.equals(userId)) {
            var user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("회원 정보를 찾을 수 없습니다."));
            user.updatePassword(
                passwordEncoder.encode(command.originPassword()),
                passwordEncoder.encode(command.newPassword())
            );
        }

        throw new IllegalArgumentException("본인의 정보만 수정 가능합니다.");
    }
}
