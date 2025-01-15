package my.fisherman.fisherman.global.redis;

import java.util.Optional;

public interface RedisRepository<T> {
    void save(String key, T value);

    Optional<T> find(String key);

    void delete(String key);
}
