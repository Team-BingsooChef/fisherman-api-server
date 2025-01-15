package my.fisherman.fisherman.auth.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import my.fisherman.fisherman.auth.domain.Authentication;
import my.fisherman.fisherman.global.redis.RedisRepository;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class AuthenticationRepository implements RedisRepository<Authentication> {
    private final RedisTemplate<String, Object> redisTemplate;
    private final ObjectMapper objectMapper;

    private final String KEY_PREFIX = "auth:";
    private final Long EXPIRE_SECONDS = 300L;
    private final TimeUnit EXPIRE_UNIT = TimeUnit.SECONDS;

    @Override
    public void save(String key, Authentication value) {
        redisTemplate.opsForValue()
                .set(KEY_PREFIX + key, value, EXPIRE_SECONDS, EXPIRE_UNIT);
    }

    @Override
    public Optional<Authentication> find(String key) {
        var value = redisTemplate.opsForValue()
                .get(KEY_PREFIX + key);
        return Optional.ofNullable(objectMapper.convertValue(value, Authentication.class));

    }

    @Override
    public void delete(String key) {
        redisTemplate.delete(KEY_PREFIX + key);
    }
}
