package com.example.ReservationPurchase.auth.repository;

import com.example.ReservationPurchase.auth.application.port.RefreshRepository;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import java.time.Duration;
import java.util.Map;

@Repository
public class RedisRefreshRepositoryImpl implements RefreshRepository {

    private final RedisTemplate<String, String> redisTemplate;
    private final HashOperations<String, String, String> hashOperations;
    private final ValueOperations<String, String> valueOperations;

    public RedisRefreshRepositoryImpl(final RedisTemplate<String, String> redisTemplate, HashOperations<String, String, String> hashOperations, ValueOperations<String, String> valueOperations) {
        this.redisTemplate = redisTemplate;
        this.hashOperations = hashOperations;
        this.valueOperations = valueOperations;
    }

    @Override
    public String save(final String refreshToken, final Long memberId, final long duration) {
        Duration expireDuration = Duration.ofSeconds(duration);
        valueOperations.set(refreshToken, String.valueOf(memberId), expireDuration);
        return refreshToken;
    }

    @Override
    public String findByValue(final String value) {
        return valueOperations.get(value);

    }

    @Override
    public void delete(final String value) {
        redisTemplate.delete(value);
    }

    @Override
    public Map<String, String> getAllFromHash(final String hashName) {
        return hashOperations.entries(hashName);
    }


    @Override
    public void removeFromHash(final String hashName, final String key) {
        hashOperations.delete(hashName, key);
    }

    @Override
    public void addToHash(final String hashName, final String key, final String value) {
        hashOperations.put(hashName, key, value);
    }

}
