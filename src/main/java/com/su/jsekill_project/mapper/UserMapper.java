package com.su.jsekill_project.mapper;


import com.su.jsekill_project.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {
    //添加用户
    int insertUser(@Param("user")User user);
    //删除用户
    int deleteUserById(@Param("userId") int userId);
    //查询用户
    User queryUser(@Param("userId") int userId);
    //修改用户
    int updateUserById(@Param("user") User user);
    //通过用户名查找用户
    User queryUserByName(@Param("username") String username);
}