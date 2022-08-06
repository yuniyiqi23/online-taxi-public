package com.mashibing.servicepassengeruser.service;

import com.mashibing.internal.dto.ResponseResult;
import com.mashibing.servicepassengeruser.dto.PassengerUser;
import com.mashibing.servicepassengeruser.mapper.PassengerUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class UserService {

    @Autowired
    PassengerUserMapper passengerUserMapper;

    public ResponseResult loginOrRegister(String passengerPhone) {
        // 根据手机号查询数据库
        HashMap<String, Object> map = new HashMap<>();
        map.put("passenger_phone", passengerPhone);
        List<PassengerUser> passengerUsers = passengerUserMapper.selectByMap(map);
        // 没有记录，注册
        // 有记录，登录
        System.out.println(passengerUsers.size() == 0 ? "没有记录": passengerUsers.get(0).getPassengerName());

        return ResponseResult.success();
    }

}
