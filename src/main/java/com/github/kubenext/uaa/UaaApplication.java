package com.github.kubenext.uaa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class UaaApplication {

    @GetMapping("/user")
    public Authentication user(Authentication authentication) {
        return authentication;
    }

    public static void main(String[] args) {
        SpringApplication.run(UaaApplication.class, args);
    }

}
