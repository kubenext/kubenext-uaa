package com.github.kubenext.uaa.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TestController {

    @GetMapping("/toSuc")
    public String suc() {
        return "suc";
    }

    @GetMapping("/rlogin")
    public String rlogin(String name) {
        return "lg";
    }


}
