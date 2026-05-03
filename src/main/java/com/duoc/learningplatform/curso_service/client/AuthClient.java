package com.duoc.learningplatform.curso_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "auth-service", url = "http://localhost:8081")
public interface AuthClient {

    @GetMapping("/api/users/{id}/exists")
    Boolean existsUserById(@PathVariable("id") Long id);

    @GetMapping("/api/users/{id}/role")
    String getUserRole(@PathVariable("id") Long id);
}