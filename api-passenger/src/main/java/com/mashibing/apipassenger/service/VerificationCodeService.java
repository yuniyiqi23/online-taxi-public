package com.mashibing.apipassenger.service;

import com.mashibing.apipassenger.remote.ServicePassengerUserClient;
import com.mashibing.apipassenger.remote.ServiceVerificationCodeClient;
import com.mashibing.internal.constant.IdentifyConstant;
import com.mashibing.internal.constant.TokenConstant;
import com.mashibing.internal.enums.CommonStatusEnum;
import com.mashibing.internal.dto.ResponseResult;
import com.mashibing.internal.request.VerificationCodeDTO;
import com.mashibing.internal.response.NumberCodeResponse;
import com.mashibing.internal.response.TokenResponse;
import com.mashibing.internal.util.JwtUtils;
import com.mashibing.internal.util.RedisKeyUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class VerificationCodeService {


    @Autowired
    ServiceVerificationCodeClient serviceVerificationCodeClient;
    @Autowired
    ServicePassengerUserClient servicePassengerUserClient;
    @Autowired
    StringRedisTemplate stringRedisTemplate;

    /**
     * 获取随机验证码
     *
     * @param phoneNumber
     * @return
     */
    public ResponseResult generateCode(String phoneNumber) {
        // 获取随机验证码
        ResponseResult<NumberCodeResponse> numberCodeResponse = serviceVerificationCodeClient.getNumberCode(6);
        int numberCode = numberCodeResponse.getData().getNumberCode();
        System.out.println("apipassenger --> phoneNumber ： " + phoneNumber);
        System.out.println("apipassenger --> 获取随机验证码 ： " + numberCode);

        // 验证码存入redis
        String redisKey = RedisKeyUtils.generateVerCodeKey(phoneNumber);
        stringRedisTemplate.opsForValue().set(redisKey, numberCode + "", 10, TimeUnit.MINUTES);
        // TODO 将验证码发送到手机上

        return ResponseResult.success();
    }


    /**
     * 校验乘客验证码
     *
     * @param phoneNumber
     * @param verificationCode
     * @return
     */
    public ResponseResult checkCode(String phoneNumber, String verificationCode) {
        // 根据phoneNumber获取redis中的验证码
        String redisKey = RedisKeyUtils.generateVerCodeKey(phoneNumber);
        String redisValue = stringRedisTemplate.opsForValue().get(redisKey);
        // 校验验证码
        // 1、验证码不存在
        if (StringUtils.isBlank(redisValue)) {
            return ResponseResult.fail(CommonStatusEnum.VERIFICATION_CODE_ERROR.getCode(),
                    CommonStatusEnum.VERIFICATION_CODE_ERROR.getValue());
        }
        // 2、验证码错误
        if (!verificationCode.trim().equals(redisValue.trim())) {
            return ResponseResult.fail(CommonStatusEnum.VERIFICATION_CODE_ERROR.getCode(),
                    CommonStatusEnum.VERIFICATION_CODE_ERROR.getValue());
        }
        // 3、验证码正确（登录或是注册）
        // TODO 删除redis验证码（只能用一次）
        VerificationCodeDTO verificationCodeDTO = new VerificationCodeDTO();
        verificationCodeDTO.setPassengerPhone(phoneNumber);
        servicePassengerUserClient.loginOrRegsiter(verificationCodeDTO);

        // 生成tokenAccess、tokenRefrash
        String tokenAccess = JwtUtils.generateToken(phoneNumber, IdentifyConstant.PASSENGER_IDENTIFY, TokenConstant.TOKEN_TYPE_ACCESS);
        String tokenRefrash = JwtUtils.generateToken(phoneNumber, IdentifyConstant.PASSENGER_IDENTIFY, TokenConstant.TOKEN_TYPE_REFRASH);
        System.out.println("token ： " + tokenAccess);
        TokenResponse tokenResponse = new TokenResponse();
        tokenResponse.setTokenAccess(tokenAccess);
        tokenResponse.setTokenRefrash(tokenRefrash);
        // token存储redis
        String tokenAccessKey = RedisKeyUtils.generateTokenKey(phoneNumber, IdentifyConstant.PASSENGER_IDENTIFY, TokenConstant.TOKEN_TYPE_ACCESS);
        stringRedisTemplate.opsForValue().set(tokenAccessKey, tokenAccess, 30, TimeUnit.DAYS);
        String tokenRefrashKey = RedisKeyUtils.generateTokenKey(phoneNumber, IdentifyConstant.PASSENGER_IDENTIFY, TokenConstant.TOKEN_TYPE_REFRASH);
        stringRedisTemplate.opsForValue().set(tokenRefrashKey, tokenRefrash, 31, TimeUnit.DAYS);

        return ResponseResult.success(tokenResponse);
    }

}
