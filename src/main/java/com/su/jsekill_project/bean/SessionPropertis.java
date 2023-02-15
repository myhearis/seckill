package com.su.jsekill_project.bean;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Classname SessionPropertis
 * @author: 我心
 * @Description: session的配置绑定类
 * @Date 2023/1/24 11:36
 * @Created by Lenovo
 */
@Component
@ConfigurationProperties(prefix = "mysession")
public class SessionPropertis {
    private String sessionClassPath;

    public String getSessionClassPath() {
        return sessionClassPath;
    }

    public void setSessionClassPath(String sessionClassPath) {
        this.sessionClassPath = sessionClassPath;
    }

}
