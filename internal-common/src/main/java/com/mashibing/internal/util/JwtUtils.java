package com.mashibing.internal.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.mashibing.internal.dto.TokenResult;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class JwtUtils {

    // salt
    private static final String SIGN = "12!@#$$";

    private static final String JWT_key_phone = "phone";
    private static final String JWT_key_identify = "identify";

    /**
     * 生成token
     * @param phone
     * @param identify
     * @return
     */
    public static String generateToken(String phone, String identify) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put(JWT_key_phone, phone);
        hashMap.put(JWT_key_identify, identify);

        // token过期时间
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, 1);
        Date date = calendar.getTime();

        JWTCreator.Builder builder = JWT.create();
        hashMap.forEach((k,v) -> {
            builder.withClaim(k, v);
        });
        builder.withExpiresAt(date);

        // 生成token
        String sign = builder.sign(Algorithm.HMAC256(SIGN));
        return sign;
    }

    /**
     * 解析token
     * @param token
     * @return
     */
    public static TokenResult parseToken(String token){
        DecodedJWT verify = JWT.require(Algorithm.HMAC256(SIGN)).build().verify(token);
        String phone = verify.getClaim(JWT_key_phone).asString();
        String identify = verify.getClaim(JWT_key_identify).asString();

        TokenResult tokenResult = new TokenResult();
        tokenResult.setPhone(phone);
        tokenResult.setIdentify(identify);
        return tokenResult;
    }

    public static void main(String[] args) {
        String token = generateToken("15112341234", "1");
        System.out.println(token);

        System.out.println(parseToken(token));
    }

}
