package com.chinasoft.mybatis_redis.service.impl;
import com.chinasoft.mybatis_redis.dao.AdminMapper;
import com.chinasoft.mybatis_redis.service.AdminService;

import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    public AdminMapper adminMapper;

    @Override
    public List<Map<String, Object>> selectUserAll() {
        return adminMapper.selectUserAll();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteUserById(Map<String, Object> map) {
        return adminMapper.deleteUserById(map);
    }
}
