package com.yugabyte.demo.iamservicewebflux.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import com.yugabyte.demo.iamservicewebflux.domain.UserProfile;
import com.yugabyte.demo.iamservicewebflux.service.UserService;
import com.yugabyte.demo.iamservicewebflux.service.UserProfileVO;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/user/profile")
    public Mono<UserProfileVO> getUserProfilesVO(@RequestParam String userId) {
        return userService.getUserProfile(userId);
    }

    @GetMapping("/user/auth")
    public Mono<UserProfileVO> authenticate(@RequestParam String userId, @RequestParam String passwordHash) {
        return userService.authenticate(userId, passwordHash);
    }
}