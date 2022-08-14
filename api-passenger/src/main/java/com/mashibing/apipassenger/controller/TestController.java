package com.mashibing.apipassenger.controller;

import com.mashibing.internal.dto.ResponseResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/")
    public String test(){

        return "Hello Api Passenger!";
    }

    @GetMapping("/authTest")
    public ResponseResult authTest(){
        return ResponseResult.success("authTest");
    }

    @GetMapping("/noAuthTest")
    public ResponseResult noAuthTest(){
        return ResponseResult.success("Test noAuth");
    }

}
