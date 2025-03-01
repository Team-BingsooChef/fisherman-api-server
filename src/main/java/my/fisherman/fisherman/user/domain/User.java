package my.fisherman.fisherman.user.domain;

import static lombok.AccessLevel.PROTECTED;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import my.fisherman.fisherman.global.exception.AuthErrorCode;
import my.fisherman.fisherman.global.exception.FishermanException;

@Entity
@Getter
@Table(name = "users")
@NoArgsConstructor(access = PROTECTED)
public class User {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private OAuthProvider oauthType;

    @Enumerated(EnumType.STRING)
    private Role role;

    private Boolean isFreshUser;

    private User(String email, String password, String nickname, OAuthProvider oauthType) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.oauthType = oauthType;
        this.role = Role.ROLE_USER;
        this.isFreshUser = true;
    }

    public static User of(String email, String password, String nickname) {
        return new User(email, password, nickname, OAuthProvider.SELF);
    }

    public static User of(String email, String nickname, OAuthProvider oauthType) {
        return new User(email, "", nickname, oauthType);
    }

    public void updateNickname(String nickname) {
        this.nickname = nickname;
    }

    public void updatePassword(String originPassword, String newPassword) {
        if (!this.password.equals(originPassword)) {
            throw new FishermanException(AuthErrorCode.INVALID_PASSWORD);
        }
        if (this.password.equals(newPassword)) {
            throw new FishermanException(AuthErrorCode.SAME_PASSWORD);
        }
        this.password = newPassword;
    }
}
