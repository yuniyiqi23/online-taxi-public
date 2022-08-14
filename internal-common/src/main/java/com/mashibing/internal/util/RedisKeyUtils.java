package com.mashibing.internal.util;

import com.mashibing.internal.constant.TokenConstant;
import org.apache.commons.lang.StringUtils;

public class RedisKeyUtils {

    // 验证码前缀
    private static String verificationCodePreFix = "Verification-Code-";

    /**
     * 生成RedisKey
     * @param phoneNumber
     * @return
     */
    public static String generateVerCodeKey(String phoneNumber) {
        return verificationCodePreFix + phoneNumber;
    }

    /**
     * 生成tokenKey
     * @param phoneNumber
     * @param identify
     * @return
     */
    public static String generateTokenKey(String phoneNumber, String identify, String tokenType) {
        return tokenType + "-" + identify + "-" + phoneNumber ;
    }

}
