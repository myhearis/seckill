package com.su.jsekill_project.configuration;

import com.google.gson.Gson;
import com.su.jsekill_project.Utils.DateUtil;
import org.springframework.context.annotation.Configuration;

/**
 * @Classname GsonConfiguration
 * @author: 我心
 * @Description:
 * @Date 2023/1/24 11:58
 * @Created by Lenovo
 */
@Configuration
public class GsonConfiguration {
    public Gson gson(){
        return new Gson();
    }
    public DateUtil dateUtil(){
        return new DateUtil();
    }
}
