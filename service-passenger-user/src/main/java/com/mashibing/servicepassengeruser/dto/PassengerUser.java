package com.mashibing.servicepassengeruser.dto;

import lombok.Data;

import java.time.LocalTime;

@Data
public class PassengerUser {

    /**
     * id
     */
    private long id;
    /**
     * 创建时间
     */
    private LocalTime gmtCreate;
    /**
     * 修改时间
     */
    private LocalTime gmtModified;
    /**
     * 乘客手机号
     */
    private String passengerPhone;
    /**
     * 乘客姓名
     */
    private String passengerName;
    /**
     * 乘客性别
     */
    private byte passengerGender;
    /**
     * 数据状态（0：无效 1：有效）
     */
    private byte state;
}
