package com.chinasoft.mybatis_redis.exception;

import org.omg.PortableInterceptor.USER_EXCEPTION;

/*---------------------------
        在构造自定义业务异常对象时使用了 枚举 的方式，将常见的业务错误提示语对应的错误代码进行
        映射，枚举类如下所示：*/
public enum  StatusCode {

    USER_NOT_FOUND(50001,"用户不存在"),
    USER_NOT_EXIST(402,"用户不存在"),
    NEED_LOGIN(401,"用户未登录"),
    REQUEST_SUCCESS(200,"用户交互正常"),
    USER_AUTHENTICATION_ERROR(50002,"用户密码不正确"),
    DATA_NULL(40404,"查询数据为空"),
    RETURN_ERROR(911,"用户交互存在异常"),
    ;

    /* public static final  int ACCOUNT_OR_PASSWORD_ERROR=401;
     public static final  int REQUEST_SUCCESS=200;
     public static final int  LOGIN_SUCCESS=202;
     public static final int  NEED_LOGIN=402;
     public static final int RETURN_ERROR=911;*/
    private  Integer code;
    private  String description;

    StatusCode(Integer code, String description) {
        this.code = code;
        this.description = description;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }
    public String getDescription(String description){
        this.description=description;
        return  description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
}
