package my.fisherman.fisherman.auth.repository;


import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import my.fisherman.fisherman.global.redis.RedisRepository;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class RefreshTokenRepository implements RedisRepository<Long> {

    private final RedisTemplate<String, Object> redisTemplate;
    private final ObjectMapper objectMapper;

    private final String REFRESH_TOKEN_PREFIX = "refresh_token:";
    private final Long REFRESH_TOKEN_EXPIRE = 60 * 60 * 24 * 7L;
    private final TimeUnit EXPIRE_UNIT = TimeUnit.SECONDS;

    @Override
    public void save(String key, Long value) {
        redisTemplate.opsForValue()
            .set(REFRESH_TOKEN_PREFIX + key, value, REFRESH_TOKEN_EXPIRE, EXPIRE_UNIT);
    }

    @Override
    public Optional<Long> find(String key) {
        Object value = redisTemplate.opsForValue()
            .get(REFRESH_TOKEN_PREFIX + key);
        return Optional.ofNullable(objectMapper.convertValue(value, Long.class));
    }

    @Override
    public void delete(String key) {
        redisTemplate.delete(REFRESH_TOKEN_PREFIX + key);
    }
}
