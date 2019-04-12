package com.github.kubenext.uaa.web.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author shangjin.li
 */
@Api("测试")
@RestController
@RequestMapping("/api")
public class Test {

    @ApiOperation("测试日期")
    @GetMapping("/test")
    public TestResult test() {
        return new TestResult();
    }

}
