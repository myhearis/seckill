package com.su.jsekill_project.bean;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Classname RedisConfigurationBean
 * @author: 我心
 * @Description: redis的配置绑定类
 * @Date 2023/1/23 13:20
 * @Created by Lenovo
 */
@Component
@ConfigurationProperties(prefix = "redis")
public class RedisConfigurationBean {
    private String ip;
    private String port;

}
