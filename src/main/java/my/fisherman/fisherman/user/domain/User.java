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
    private Boolean isPublic;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private OAuthProvider oauthType;

    @Enumerated(EnumType.STRING)
    private Role role;

    private Boolean isFreshUser;

    private User(
        String email, String password, String nickname, Boolean isPublic, OAuthProvider oauthType) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.isPublic = isPublic;
        this.oauthType = oauthType;
        this.role = Role.ROLE_USER;
        this.isFreshUser = true;
    }

    public static User of(String email, String password, String nickname) {
        return new User(email, password, nickname, true, OAuthProvider.SELF);
    }

    public static User of(String email, String nickname, OAuthProvider oauthType) {
        return new User(email, "", nickname, true, oauthType);
    }

    public void updateNickname(String nickname) {
        this.nickname = nickname;
    }

    public void updatePublic(boolean isPublic) {
        this.isPublic = isPublic;
    }

    public void updatePassword(String originPassword, String newPassword) {
        if (!this.password.equals(originPassword)) {
            throw new IllegalArgumentException("기존 비밀번호가 일치하지 않습니다.");
        }
        if (this.password.equals(newPassword)) {
            throw new IllegalArgumentException("기존 비밀번호와 새 비밀번호가 동일합니다.");
        }
        this.password = newPassword;
    }
}
