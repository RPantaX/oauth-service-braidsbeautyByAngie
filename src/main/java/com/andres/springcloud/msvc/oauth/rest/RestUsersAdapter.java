package com.andres.springcloud.msvc.oauth.rest;

import com.andres.springcloud.msvc.oauth.models.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-service")
public interface RestUsersAdapter {

    @GetMapping("/v1/user-service/user/username/{username}")
    User getUserByUsername(@PathVariable String username);
}
