package com.chinasoft.mybatis_redis.exception;

/*自定义异常类  记得继承 运行异常父类*/
public class MyException extends RuntimeException{
    private Integer code;
    public MyException(StatusCode userResponseEnum){
        super(userResponseEnum.getDescription());
        this.code=userResponseEnum.getCode();
    }
    public MyException(StatusCode userResponseEnum,String message){
        super(userResponseEnum.getDescription(message));
        this.code=userResponseEnum.getCode();
    }
    public MyException(StatusCode userResponseEnum,int code,String message){
        super(userResponseEnum.getDescription(message));
        this.code=code;
    }
    public Integer getCode(){
        return  code;
    }

}


