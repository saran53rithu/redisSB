package com.example.redis;

import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class DemoController {

    private final DemoService demoService;

    public DemoController(DemoService demoService) {
        this.demoService = demoService;
    }

    @GetMapping("/greet/{name}")
    public String greet(@PathVariable String name) {
        return demoService.getGreeting(name);
    }

    @GetMapping("/greet/cache/{name}")
    public String getCachedGreeting(@PathVariable String name) {
        String greeting = demoService.getCachedGreeting(name); // Directly checks Redis
        return greeting != null ? greeting : "No cached greeting found for " + name;
    }

    @GetMapping("/user/{username}")
    public String getUser(@PathVariable String username) {
        return demoService.getUserByUsername(username);
    }
    @GetMapping("/user/cache/all")
    public Map<String, String> getAllCachedUsers() {
        return demoService.getAllUsersFromCache();
    }
}