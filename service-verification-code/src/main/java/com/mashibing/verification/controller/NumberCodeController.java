package com.mashibing.verification.controller;

import com.mashibing.internal.dto.ResponseResult;
import com.mashibing.internal.response.NumberCodeResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NumberCodeController {

    @GetMapping("/numberCode/{size}")
    public ResponseResult getNumberCode(@PathVariable("size") int size) {
        // 随机生成验证码
        double random = (Math.random() * 9 + 1) * Math.pow(10, size - 1);
        int randomInt = (int) random;
        System.out.println("verification --> 生成随机验证码 ： " + randomInt);

        // 定义返回值
        NumberCodeResponse response =  new NumberCodeResponse();
        response.setNumberCode(randomInt);
        return new ResponseResult().success(response);
    }

}
