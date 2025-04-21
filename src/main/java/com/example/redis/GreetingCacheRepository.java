package com.example.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class GreetingCacheRepository {

    private RedisTemplate<String, String> redisTemplate = new RedisTemplate<>();

    // Constructor injection
    public GreetingCacheRepository(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

//    @Autowired
//    private static RedisTemplate<String, String> redisTemplate;
//
    private final String CACHE_PREFIX = "greeting::";
//
//    public GreetingCacheRepository(RedisTemplate<String, String> redisTemplate) {
//        this.redisTemplate = redisTemplate;
//    }

//    public Map<String, String> findAllUsersFromCache() {
//        Set<String> keys = redisTemplate.keys("user::*");
//        if (keys == null || keys.isEmpty()) {
//            return Collections.emptyMap();
//        }
//
//        List<String> values = redisTemplate.opsForValue().multiGet(keys);
//
//        Map<String, String> result = new HashMap<>();
//        int i = 0;
//        for (String key : keys) {
//            result.put(key.replace("user::", ""), values.get(i++));
//        }
//        return result;
//    }

    public Map<String, String> findAllUsersFromCache() {
        Set<String> keys = redisTemplate.keys("user::*");

        if (keys == null || keys.isEmpty()) {
            return Collections.emptyMap();
        }

        List<String> values = redisTemplate.opsForValue().multiGet(keys);

        Map<String, String> result = new HashMap<>();
        int i = 0;
        for (String key : keys) {
            String cleanedKey = key.replaceFirst("^user::", "");
            String value = values.get(i++);

            // Remove "User-" prefix from value if present
            String cleanedValue = value != null ? value.replaceFirst("^User-", "") : "";

            result.put(cleanedKey, cleanedValue);
        }

        return result;
    }


    public String getGreetingFromCache(String name) {
        return redisTemplate.opsForValue().get(CACHE_PREFIX + name);
    }

    public void putGreetingInCache(String name, String greeting) {
        redisTemplate.opsForValue().set(CACHE_PREFIX + name, greeting);
    }

    private final String USER_CACHE_PREFIX = "user::";

}
