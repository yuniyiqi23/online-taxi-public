package com.mashibing.internal.dto;

import lombok.Data;

@Data
public class TokenResult {

    /**
     * 手机号
     */
    private String phone;
    /**
     * 身份 1：乘客 2：司机
     */
    private String identify;

}
