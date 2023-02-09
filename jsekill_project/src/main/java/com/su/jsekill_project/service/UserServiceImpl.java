package com.su.jsekill_project.service;

import com.su.jsekill_project.mapper.UserMapper;
import com.su.jsekill_project.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

/**
 * @Classname UserServiceImpl
 * @author: 我心
 * @Description:
 * @Date 2023/1/17 22:49
 * @Created by Lenovo
 */
@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserMapper userMapper;
    @Override
    public int addUser(User user) {
        return userMapper.insertUser(user);
    }

    @Override
    public int deleteUserById(int userId) {
        return userMapper.deleteUserById(userId);
    }

    @Override
    public User getUserById(int userId) {
        return userMapper.queryUser(userId);
    }

    @Override
    public User getUserByName(String username) {
        User user = userMapper.queryUserByName(username);
        return user;
    }

    //判断用户是否存在
    @Override
    public boolean userIsExist(String userName) {
        boolean re=false;
        User user = userMapper.queryUserByName(userName);
        if (user!=null){
            re=true;
        }
        return re;
    }

    @Override
    public boolean loginProcess(User user) {
        boolean re=false;//返回值

        //查询该用户是否存在
        boolean isExist = userIsExist(user.getUserName());
        //获取该用户的数据库数据
        User userDataBase = userMapper.queryUserByName(user.getUserName());
        //判断密码是否正确
        if (userDataBase.getPassword().equals(user.getPassword())){
            //登录成功
            re=true;
        }
        else {
            //登录失败
            re=false;
        }
        //查询用户的用户名
        return re;
    }

    @Override
    public boolean isUserLogin(HttpSession session) {
        boolean re=false;
        //获取session
        if (session!=null){
            Object user = session.getAttribute("user");
            if (user!=null){
                re=true;
            }
        }
        return false;
    }

    @Override
    public boolean exitUser(HttpSession session) {
        if (session!=null){
            session.setAttribute("user",null);
            session.setAttribute("username",null);
            session.setAttribute("userId",null);
            return true;
        }
        return false;
    }
}
