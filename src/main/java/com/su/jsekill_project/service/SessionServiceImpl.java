package com.su.jsekill_project.service;

import com.google.gson.Gson;
import com.su.jsekill_project.bean.SessionPropertis;
import com.su.jsekill_project.constant.RedisKeyPrefix;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

/**
 * @Classname SessionServiceImpl
 * @author: 我心
 * @Description:
 * @Date 2023/1/24 11:55
 * @Created by Lenovo
 */
@Service
public class SessionServiceImpl implements SessionService{
    //session的配置对象
    @Autowired
    private SessionPropertis sessionPropertis;
    //redis操作模板对象
    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    private Gson gson;

//    从redis中获取session对象
    @Override
    public HttpSession getSessionInRedis(String sessionId) {
        //获取session的class对象
        try {
            Class<HttpSession> httpSessionClass = (Class<HttpSession>) Class.forName(sessionPropertis.getSessionClassPath());
            Class<?> aClass = Class.forName(sessionPropertis.getSessionClassPath());
            String key= RedisKeyPrefix.SESSION_PRE+sessionId;
            String sessionJsonStr = redisTemplate.opsForValue().get(key);
            Object o = gson.fromJson(sessionJsonStr, httpSessionClass);
            return (HttpSession) o;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();

        }


        return null;
    }

    @Override
    public String toJsonString(HttpSession session) {
        return null;
    }

    @Override
    public boolean saveSessionToRedis(HttpSession session) {
        return false;
    }
}
