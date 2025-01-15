package my.fisherman.fisherman.user.domain;

import static lombok.AccessLevel.PROTECTED;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;

@Entity
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

    private User(String email, String password, String nickname, Boolean isPublic, OAuthProvider oauthType) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.isPublic = isPublic;
        this.oauthType = oauthType;
        this.role = Role.ROLE_USER;
    }

    public static User from(String email, String password, String nickname) {
        return new User(email, password, nickname, true, OAuthProvider.SELF);
    }
}