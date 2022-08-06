package com.mashibing.internal.constant;

import lombok.Getter;

public enum CommonStatusEnum {

    /**
     * 成功
     */
    SUCCESS(1,"success"),
    /**
     * 失败
     */
    FAIL(0,"fail"),
    /**
     * 验证码错误
     */
    VERIFICATION_CODE_ERROR(1099, "验证码错误")
    ;

    @Getter
    private int code;
    @Getter
    private String value;

    CommonStatusEnum(int code, String value) {
        this.code = code;
        this.value = value;
    }
}
