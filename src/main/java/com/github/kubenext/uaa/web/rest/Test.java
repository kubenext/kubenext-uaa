package com.github.kubenext.uaa.web.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author shangjin.li
 */
@RestController
@RequestMapping("/api")
public class Test {

    @GetMapping("/test")
    public TestResult test() {
        return new TestResult();
    }

}
