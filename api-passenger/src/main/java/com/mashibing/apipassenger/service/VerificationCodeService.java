package com.mashibing.apipassenger.service;

import com.mashibing.apipassenger.remote.ServicePassengerUserClient;
import com.mashibing.apipassenger.remote.ServiceVerificationCodeClient;
import com.mashibing.internal.constant.CommonStatusEnum;
import com.mashibing.internal.dto.ResponseResult;
import com.mashibing.internal.request.VerificationCodeDTO;
import com.mashibing.internal.response.NumberCodeResponse;
import com.mashibing.internal.response.TokenResponse;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class VerificationCodeService {

    private static String verificationCodePreFix = "Verification-Code-";

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
        String redisKey = generateRedisKey(phoneNumber);
        stringRedisTemplate.opsForValue().set(redisKey, numberCode + "", 2, TimeUnit.MINUTES);
        System.out.println("验证码存入redis");
        // TODO 将验证码发送到手机上

        return ResponseResult.success();
    }

    /**
     * 生成RedisKey
     *
     * @param phoneNumber
     * @return
     */
    public static String generateRedisKey(String phoneNumber) {
        return verificationCodePreFix + phoneNumber;
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
        String redisKey = generateRedisKey(phoneNumber);
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
        VerificationCodeDTO verificationCodeDTO = new VerificationCodeDTO();
        verificationCodeDTO.setPassengerPhone(phoneNumber);
        servicePassengerUserClient.loginOrRegsiter(verificationCodeDTO);
        // 返回token
        TokenResponse tokenResponse = new TokenResponse();
        tokenResponse.setToken("111111");
        return ResponseResult.success(tokenResponse);
    }

}
