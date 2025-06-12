package my.fisherman.fisherman.security.application.dto;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import my.fisherman.fisherman.user.domain.OAuthProvider;
import my.fisherman.fisherman.user.domain.Role;
import my.fisherman.fisherman.user.domain.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

public record UserPrinciple(
    Long id,
    String email,
    String password,
    String nickname,
    OAuthProvider oauthType,
    Role role,
    Boolean isFreshUser
) implements OAuth2User, UserDetails {

    public static UserPrinciple from(User user) {
        return new UserPrinciple(
            user.getId(), user.getEmail(), user.getPassword(),
            user.getNickname(), user.getOauthType(), user.getRole(),
            user.getIsFreshUser());
    }

    @Override
    public Map<String, Object> getAttributes() {
        return Map.of(
            "id", id,
            "email", email,
            "nickname", nickname,
            "oauthType", oauthType,
            "role", role
        );
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority authorize = new SimpleGrantedAuthority(role.name());
        return List.of(authorize);
    }

    @Override
    public String getName() {
        return email;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }
}
