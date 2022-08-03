package com.mashibing.verification.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NumberCodeController {

    @GetMapping("/numberCode/{size}")
    public String getNumberCode(@PathVariable("size") int size) {
        // 随机生成验证码
        double random = (Math.random() * 9 + 1) * Math.pow(10, size - 1);
        int randomInt = (int) random;
        return null;
    }

}
