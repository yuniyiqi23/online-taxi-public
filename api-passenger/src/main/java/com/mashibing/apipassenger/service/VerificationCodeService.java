package com.mashibing.apipassenger.service;

import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class VerificationCodeService {

    public String generateCode(String phone){
        // 获取手机号码
        System.out.println("获取手机号码 ： " + phone);
        // 验证码存入redis
        System.out.println("验证码存入redis");

        JSONObject result = new JSONObject();
        result.put("code", 200);
        result.put("message", "success");
        return result.toString();
    }

}
