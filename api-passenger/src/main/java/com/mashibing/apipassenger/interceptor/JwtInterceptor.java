package com.mashibing.apipassenger.interceptor;

import com.mashibing.internal.constant.TokenConstant;
import com.mashibing.internal.dto.ResponseResult;
import com.mashibing.internal.dto.TokenResult;
import com.mashibing.internal.util.JwtUtils;
import com.mashibing.internal.util.RedisKeyUtils;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

public class JwtInterceptor implements HandlerInterceptor {

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        boolean result = true;
        String resultString = null;

        // 获取token
        String token = request.getHeader("Authorization");
        // 判断是否为空
        if (StringUtils.isBlank(token)) {
            result = false;
        }
        // 对比redis存储的token
        try {
            TokenResult tokenResult = JwtUtils.parseToken(token);
            String phone = tokenResult.getPhone();
            String identify = tokenResult.getIdentify();
            String tokenKey = RedisKeyUtils.generateTokenKey(phone, identify, TokenConstant.TOKEN_TYPE_ACCESS);
            String redisValue = stringRedisTemplate.opsForValue().get(tokenKey);
            if (StringUtils.isBlank(redisValue) || !redisValue.trim().equals(token.trim())) {
                result = false;
            }
        } catch (Exception e) {
            result = false;
            resultString = "token invalued";
        }

        if (!result) {
            PrintWriter writer = response.getWriter();
            writer.print(JSONObject.fromObject(ResponseResult.fail(resultString)).toString());
        }

        return result;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }
}
