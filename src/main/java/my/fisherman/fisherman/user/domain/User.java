package my.fisherman.fisherman.user.domain;

import static lombok.AccessLevel.PROTECTED;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;
import my.fisherman.fisherman.common.domain.BaseEntity;

@Entity(name = "users")
@NoArgsConstructor(access = PROTECTED)
public class User extends BaseEntity {
    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private Boolean isPublic;

    @Column(nullable = false)
    private OAuthProvider oauthType;

    private User(String email, String password, String nickname, Boolean isPublic, OAuthProvider oauthType) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.isPublic = isPublic;
        this.oauthType = oauthType;
    }
}
