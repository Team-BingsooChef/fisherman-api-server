package my.fisherman.fisherman.user.application;

import lombok.RequiredArgsConstructor;
import my.fisherman.fisherman.auth.repository.AuthenticationRepository;
import my.fisherman.fisherman.fishingspot.domain.FishingSpot;
import my.fisherman.fisherman.fishingspot.repository.FishingSpotRepository;
import my.fisherman.fisherman.global.exception.AuthErrorCode;
import my.fisherman.fisherman.global.exception.FishermanException;
import my.fisherman.fisherman.global.exception.code.UserErrorCode;
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

        var authResult = authenticationRepository.find(email)
            .orElseThrow();
        if (authResult.notVerified()) {
            throw new FishermanException(AuthErrorCode.EMAIL_NOT_VERIFIED);
        }

        userRepository.findUserByEmailWithWriteLock(email)
            .ifPresent(user -> {
                throw new FishermanException(UserErrorCode.ALREADY_EXISTS);
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
            .orElseThrow(() -> new FishermanException(UserErrorCode.FORBIDDEN));

        if (currentUserId.equals(userId)) {
            var user = userRepository.findById(userId)
                .orElseThrow(() -> new FishermanException(UserErrorCode.NOT_FOUND));
            return UserInfo.Simple.from(user);
        }

        throw new FishermanException(UserErrorCode.FORBIDDEN);
    }

    @Transactional(readOnly = true)
    public UserInfo.Detail getMyDetailInfo() {
        var currentUserId = SecurityUtil.getCurrentUserId()
            .orElseThrow(() -> new FishermanException(UserErrorCode.FORBIDDEN));

        if (currentUserId == null) {
            throw new FishermanException(UserErrorCode.FORBIDDEN);
        }

        var user = userRepository.findById(currentUserId)
            .orElseThrow(() -> new FishermanException(UserErrorCode.NOT_FOUND));
        return UserInfo.Detail.from(user);
    }

    @Transactional
    public void updateNickname(Long userId, UserCommand.UpdateNickname command) {
        var currentUserId = SecurityUtil.getCurrentUserId()
            .orElseThrow(() -> new FishermanException(UserErrorCode.FORBIDDEN));
        if (currentUserId.equals(userId)) {
            var user = userRepository.findById(userId)
                .orElseThrow(() -> new FishermanException(UserErrorCode.NOT_FOUND));
            user.updateNickname(command.nickname());
        }

        throw new FishermanException(UserErrorCode.FORBIDDEN);
    }

    @Transactional
    public void updatePublic(Long userId, UserCommand.UpdatePublic command) {
        var currentUserId = SecurityUtil.getCurrentUserId()
            .orElseThrow(() -> new FishermanException(UserErrorCode.FORBIDDEN));
        if (currentUserId.equals(userId)) {
            var user = userRepository.findById(userId)
                .orElseThrow(() -> new FishermanException(UserErrorCode.NOT_FOUND));
            user.updatePublic(command.isPublic());
        }

        throw new FishermanException(UserErrorCode.FORBIDDEN);
    }

    public void updatePassword(Long userId, UpdatePassword command) {
        var currentUserId = SecurityUtil.getCurrentUserId()
            .orElseThrow(() -> new FishermanException(UserErrorCode.FORBIDDEN));
        if (currentUserId.equals(userId)) {
            var user = userRepository.findById(userId)
                .orElseThrow(() -> new FishermanException(UserErrorCode.NOT_FOUND));
            user.updatePassword(
                passwordEncoder.encode(command.originPassword()),
                passwordEncoder.encode(command.newPassword())
            );
        }

        throw new FishermanException(UserErrorCode.FORBIDDEN);
    }
}
