package com.su.jsekill_project.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.data.redis.config.ConfigureRedisAction;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * @Classname HttpSessionConfiguration
 * @author: 我心
 * @Description: session的自动配置
 * @Date 2023/1/25 10:34
 * @Created by Lenovo
 */
@Configuration
//设置session过期时间
@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 30*60)
public class HttpSessionConfiguration {
    @Bean
    public  ConfigureRedisAction configureRedisAction(){
        ConfigureRedisAction noOp = ConfigureRedisAction.NO_OP;
        return noOp;
    }
}
