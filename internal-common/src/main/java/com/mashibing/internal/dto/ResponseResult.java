package com.mashibing.internal.dto;

import com.mashibing.internal.constant.CommonStatusEnum;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ResponseResult<T> {

    private int code;
    private String message;
    private T data;

    /**
     * 成功的方法
     *
     * @param data
     * @param <T>
     * @return
     */
    public static <T> ResponseResult success(T data) {
        return new ResponseResult().setCode(CommonStatusEnum.SUCCESS.getCode())
                .setMessage(CommonStatusEnum.SUCCESS.getValue())
                .setData(data);
    }

    /**
     * 统一的返回错误信息
     * @param data
     * @param <T>
     * @return
     */
    public static <T> ResponseResult fail(T data){
        return new ResponseResult().setData(data);
    }

    /**
     * 自定义失败错误码
     * @param code
     * @param msg
     * @return
     */
    public static ResponseResult fail(int code, String msg) {
        return new ResponseResult().setCode(code).setMessage(msg);
    }


    /**
     * 自定义错误码，提示信息
     * @param code
     * @param msg
     * @param data
     * @return
     */
    public static ResponseResult fail(int code, String msg, String data) {
        return new ResponseResult().setCode(code).setMessage(msg).setData(data);
    }

}
