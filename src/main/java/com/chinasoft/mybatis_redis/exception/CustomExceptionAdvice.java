package com.chinasoft.mybatis_redis.exception;

import com.chinasoft.mybatis_redis.util.FinalMsg;
import com.chinasoft.mybatis_redis.util.ReturnData;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/*后端 异常抛出的捕获类
 * 处理与用户相关的业务异常*/
@RestController
@ResponseBody
@ControllerAdvice(basePackages = {"com.chinasoft.mybatis_redis.controller",
        "com.chinasoft.mybatis_redis.service.impl"})
public class CustomExceptionAdvice {
    /*一旦捕获自定义异常  记得slf4j 打印一下日志记录*/
    private  static  final Logger logger=
            LoggerFactory.getLogger(CustomExceptionAdvice.class);

    @ExceptionHandler(value = MyException.class)
    public ReturnData userExceptionHandle(HttpServletRequest request,MyException e){
        logger.error("错误码:{},错误信息:{},用户信息异常：Host:{},invoke URL:{}",
                e.getCode(),e.getMessage(),request.getRemoteHost(),request.getRequestURL());
        return new ReturnData(e.getCode(),e.getMessage());
    }
}
