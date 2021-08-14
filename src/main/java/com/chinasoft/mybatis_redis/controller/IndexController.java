package com.chinasoft.mybatis_redis.controller;

import com.chinasoft.mybatis_redis.exception.MyException;
import com.chinasoft.mybatis_redis.exception.StatusCode;
import com.chinasoft.mybatis_redis.service.AdminService;
import com.chinasoft.mybatis_redis.util.*;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class IndexController {
    @Resource
    private AdminService adminService;

    @RequestMapping("/getUserAll")
    public Object getUserAll(HttpServletRequest request){
        List<Map<String,Object>> list=adminService.selectUserAll();
        if(list.size()>0){
            return  new ReturnData(StatusCode.REQUEST_SUCCESS,list,"查询数据结果正常！");
        }else{
            throw  new MyException(StatusCode.DATA_NULL,"无查询数据结果");
        }
    }
    @RequestMapping("/delUser")
    public Object delUser(@RequestParam Map<String,Object> map){

        if(adminService.deleteUserById(map)){
            return  new ReturnData(StatusCode.REQUEST_SUCCESS,"删除数据结果正常！");
        }else{
            throw  new MyException(StatusCode.DATA_NULL,"无删除数据结果");
        }
    }


}
