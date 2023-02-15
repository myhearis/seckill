package com.su.jsekill_project.service;

import com.su.jsekill_project.pojo.User;

import javax.servlet.http.HttpSession;

/**
 * @Classname UserService
 * @author: 我心
 * @Description:
 * @Date 2023/1/17 22:02
 * @Created by Lenovo
 */
public interface UserService {
//    添加用户
    int addUser(User user);
//    删除用户
    int deleteUserById(int userId);
//    查找用户,通过id
    User getUserById(int userId);
//    查找用户，通过用户名
    User getUserByName(String username);
//    判断用户是否存在
    boolean userIsExist(String userName);
//    处理用户登录逻辑，登录成功返回true
    boolean loginProcess(User user);
    //判断是否已经登录
    boolean isUserLogin(HttpSession session);
    //用户退出登录业务方法
    boolean exitUser(HttpSession session);
}
