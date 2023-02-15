package com.su.jsekill_project.controller;

import com.google.gson.Gson;
import com.su.jsekill_project.Utils.ObjectUtils;
import com.su.jsekill_project.constant.RedisKeyPrefix;
import com.su.jsekill_project.pojo.User;
import com.su.jsekill_project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @Classname LoginController
 * @author: 我心
 * @Description:
 * @Date 2023/1/23 22:11
 * @Created by Lenovo
 */
@Controller
@RequestMapping("/jsekill")
public class LoginController {
    @Autowired
    StringRedisTemplate redisTemplate;
    @Autowired
    UserService userService;
    @GetMapping("/login")
    public String login(){
        return "/login";
    }
    @PostMapping("/loginProcess")
    public String loginProcess(@RequestParam("username") String username, @RequestParam("password") String password, HttpServletRequest request, HttpServletResponse response){
        User user=new User();
        user.setUserName(username);
        user.setPassword(password);
        boolean isNeedLogin=true;//是否需要进行登陆业务逻辑,默认需要

        //判断当前请求的用户是否已经登陆了
       //获取session，但不是新建
        HttpSession session = request.getSession(false);
        if (session!=null){
           //如果session不为空，则判断当前登陆的是否是请求的用户
            Object name = session.getAttribute("username");
            if (name!=null){
                String nameStr= (String) name;
                if (name.equals(username))
                    isNeedLogin=false;
            }
        }
        else {
            isNeedLogin=true;
        }
        if (isNeedLogin){
            boolean b = userService.loginProcess(user);
            //登陆成功
            if (b){
                //创建一个新的session
                HttpSession thisUserSession = request.getSession(true);
                thisUserSession.setAttribute("username",username);
                //将用户的密码去除，写入session域中
                User userByName = userService.getUserByName(username);
                thisUserSession.setAttribute("user",userByName);
                //将用户的id写入
                thisUserSession.setAttribute("userId",userByName.getUserId());
//                System.out.println("SESSION_ID="+thisUserSession.getId());
                //请求转发到index,返回的是视图名称
                return "/index";
            }
            //登陆信息错误
            else {
                //重定向,返回的是url映射到控制器方法
                return "redirect:/jsekill/login";
            }
        }
//        System.out.println("SESSION_ID="+session.getId());

        //到这里代表用户已经登陆过了
        return "/index";
    }
}
