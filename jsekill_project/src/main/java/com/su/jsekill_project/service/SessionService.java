package com.su.jsekill_project.service;

import javax.servlet.http.HttpSession;

/**
 * @Classname SessionService
 * @author: 我心
 * @Description: 关于session的业务接口
 * @Date 2023/1/24 11:26
 * @Created by Lenovo
 */
public interface SessionService {
    //从redis中获取session对象
    HttpSession getSessionInRedis(String sessionId);
    //将session对象转化为json字符串
    String toJsonString(HttpSession session);
    //将session存入redis中
    boolean saveSessionToRedis(HttpSession session);
}
