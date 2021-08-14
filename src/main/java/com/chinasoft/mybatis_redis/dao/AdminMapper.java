package com.chinasoft.mybatis_redis.dao;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
@Repository
@Mapper
public interface AdminMapper {
public List<Map<String,Object>> selectUserAll();
public boolean deleteUserById(Map<String,Object> map);
}
