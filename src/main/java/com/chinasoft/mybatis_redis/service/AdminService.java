package com.chinasoft.mybatis_redis.service;

import java.util.List;
import java.util.Map;

public interface AdminService {
    public List<Map<String,Object>> selectUserAll();
    public boolean deleteUserById(Map<String,Object> map);
}
