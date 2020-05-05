package com.chenrj.zhihu.result;

/**
 * @ClassName Result
 * @Description
 * @Author rjchen
 * @Date 2020-05-05 9:45
 * @Version 1.0
 */
public enum ResultStatus {

    /**
     * 注册登录
     */
    REGISTER_SUCCESS(20000, "注册成功"),
    REGISTER_FAIL(20001, "此用户已被注册, 请直接登录或者更换其他用户名注册"),
    LOGIN_SUCCESS(20002,"登录成功"),
    LOGIN_FAIL(20003,"用户名或者密码错误");


    private int code;
    private String message;

    ResultStatus(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
