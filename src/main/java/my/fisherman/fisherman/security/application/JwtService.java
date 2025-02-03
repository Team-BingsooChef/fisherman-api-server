package my.fisherman.fisherman.security.application;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Jwts.SIG;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import my.fisherman.fisherman.security.application.dto.UserPrinciple;
import my.fisherman.fisherman.security.config.property.JwtProperties;

public class JwtService {
    private final SecretKey accessSecret;
    private final SecretKey refreshSecret;
    private final Long accessTokenExpiredTime;
    private final Long refreshTokenExpiredTime;

    public JwtService(JwtProperties jwtProperties) {
        this.accessSecret = new SecretKeySpec(jwtProperties.accessSecret().getBytes(StandardCharsets.UTF_8),
                SIG.HS256.key().build().getAlgorithm());
        this.refreshSecret = new SecretKeySpec(jwtProperties.refreshSecret().getBytes(StandardCharsets.UTF_8),
                SIG.HS256.key().build().getAlgorithm());
        this.accessTokenExpiredTime = jwtProperties.accessTokenExpiration();
        this.refreshTokenExpiredTime = jwtProperties.refreshTokenExpiration();
    }

    public String createAccessToken(UserPrinciple userPrinciple) {
        var time = System.currentTimeMillis();
        return Jwts.builder()
                .claim("id", userPrinciple.id())
                .claim("role", userPrinciple.role())
                .issuedAt(new Date(time))
                .expiration(new Date(time + accessTokenExpiredTime))
                .signWith(accessSecret)
                .compact();
    }

    public String createRefreshToken(UserPrinciple userPrinciple) {
        var time = System.currentTimeMillis();
        return Jwts.builder()
                .claim("id", userPrinciple.id())
                .issuedAt(new Date(time))
                .expiration(new Date(time + refreshTokenExpiredTime))
                .signWith(refreshSecret)
                .compact();
    }
}
