package com.daegeon.bread2u.global.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class RedisService {

    private final RedisTemplate<String, Object> redisTemplate;

    /**
     * Redis Key, Value 저장
     *
     * @param key
     * @param value
     */
    public void setValues(String key, String value) {
        ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
        valueOperations.set(key, value);
    }

    /**
     * Redis Key, Value, Duration 저장
     *
     * @param key
     * @param data
     * @param duration
     */
    public void setValues(String key, String data, Duration duration) {
        ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
        valueOperations.set(key, data, duration);
    }

    @Transactional(readOnly = true)
    public String getValues(String key) {
        ValueOperations<String, Object> values = redisTemplate.opsForValue();
        return (String) values.get(key);
    }

    public void deleteValues(String key) {
        //Delete given key.
        redisTemplate.delete(key);
    }

    public void expireValues(String key, int timeout) {
        //Set time to live for given key.
        redisTemplate.expire(key, timeout, TimeUnit.MILLISECONDS);
    }

    public boolean checkExistsValue(String key) {
        String value = getValues(key);
        return value != null;
    }
}