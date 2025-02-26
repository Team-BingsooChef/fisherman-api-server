package my.fisherman.fisherman.security.application;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import my.fisherman.fisherman.fishingspot.domain.FishingSpot;
import my.fisherman.fisherman.fishingspot.repository.FishingSpotRepository;
import my.fisherman.fisherman.inventory.domain.Inventory;
import my.fisherman.fisherman.inventory.repository.InventoryRepository;
import my.fisherman.fisherman.security.application.command.SecurityUserCommand;
import my.fisherman.fisherman.security.application.dto.UserPrinciple;
import my.fisherman.fisherman.user.domain.OAuthProvider;
import my.fisherman.fisherman.user.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
public class SecurityUserService implements UserDetailsService {

    public final UserRepository userRepository;
    private final InventoryRepository inventoryRepository;
    private final FishingSpotRepository fishingSpotRepository;

    @Transactional
    public UserPrinciple signUp(SecurityUserCommand.OAuthSignUp command) {
        userRepository.findUserByEmailWithWriteLock(command.email())
            .ifPresent(user -> {
                throw new OAuth2AuthenticationException(
                    "이미 가입된 이메일입니다.[%s]".formatted(user.getOauthType()));
            });

        var user = command.toEntity();
        userRepository.save(user);

        var inventory = Inventory.of(user);
        var fishingSpot = FishingSpot.of(user);
        inventoryRepository.save(inventory);
        fishingSpotRepository.save(fishingSpot);
        
        return UserPrinciple.from(user);
    }

    @Transactional(readOnly = true)
    public Optional<UserPrinciple> getUserInfo(String email, String oauthProvider) {
        var oauthType = OAuthProvider.of(oauthProvider);
        return userRepository.findUserByEmailAndOauthType(email, oauthType)
            .map(UserPrinciple::from);
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findUserByEmailAndOauthType(username, OAuthProvider.SELF)
            .map(UserPrinciple::from)
            .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));
    }
}
