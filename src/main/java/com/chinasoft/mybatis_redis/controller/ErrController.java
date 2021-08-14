package com.chinasoft.mybatis_redis.controller;

import com.chinasoft.mybatis_redis.exception.MyException;
import com.chinasoft.mybatis_redis.exception.StatusCode;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class ErrController implements ErrorController {
    private static int statusCode;
    @RequestMapping("/")
    public String showIndex(HttpServletRequest request) {

        return "index";
    }

    @RequestMapping("/error")
    @ResponseBody
    public String showError(HttpServletRequest request, HttpServletResponse response) {
//System.out.println(response.getStatus()+">>>>>>>>>>");
        statusCode=response.getStatus();
        return getErrorPath();
    }


    public String getErrorPath() {
        throw new MyException(StatusCode.RETURN_ERROR,statusCode,"抱歉，请求异常！");
    }
}
