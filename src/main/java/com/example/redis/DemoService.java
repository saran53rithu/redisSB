package com.example.redis;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class DemoService {

    private final GreetingCacheRepository cacheRepo;

    public DemoService(GreetingCacheRepository cacheRepo) {
        this.cacheRepo = cacheRepo;
    }

    @Cacheable("greeting")
    public String getGreeting(String name) {
        simulateSlowService();
        return "Hello, " + name + "!";
    }

    private void simulateSlowService() {
        try {
            Thread.sleep(3000); // simulate delay
        } catch (InterruptedException e) {
            throw new IllegalStateException(e);
        }
    }

    public String getCachedGreeting(String name) {
        return cacheRepo.getGreetingFromCache(name);
    }

    @Cacheable(value = "user", key = "#username")
    public String getUserByUsername(String username) {
        simulateSlowService();
        return "User-" + username;
    }

    public Map<String, String> getAllUsersFromCache() {
        return cacheRepo.findAllUsersFromCache();
    }
}