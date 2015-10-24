package spring.sample.rest.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@Repository
public class RedisRepository {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    /**
     * Redis string operations
     */
    public void put(String key, String value) {
        redisTemplate.opsForValue().set(key, value);
    }

    public void multiPut(Map<String, String> keyValues) {
        redisTemplate.opsForValue().multiSet(keyValues);
    }

    public String get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public List<String> multiGet(Collection<String> keys) {
        return redisTemplate.opsForValue().multiGet(keys);
    }

    public void delete(String key) {
        redisTemplate.delete(key);
    }

    public void delete(Collection<String> key) {
        redisTemplate.delete(key);
    }

    // Implement the rest of redis operations
}
