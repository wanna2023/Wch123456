package com.xyu.mapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
@Mapper
public interface UserMapper {
    public String selectPasswordByUsername(String name);
    public void creatUser(@Param("username") String username, @Param("password") String password);
}
