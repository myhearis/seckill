package com.su.jsekill_project.Utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.su.jsekill_project.pojo.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jws.soap.SOAPBinding;

/**
 * @Classname ObjectUtils
 * @author: 我心
 * @Description: 用于格式化或者解析对象的工具类
 * @Date 2023/1/12 22:16
 * @Created by Lenovo
 */
public class ObjectUtils {
    private static final Logger logger = LoggerFactory.getLogger(ObjectUtils.class);
    private ObjectMapper objectMapper=new ObjectMapper();
    //传入指定对象，转换为json字符串
    public  String  toJsonStr(Object object){
        String re=null;
        try {
            re = objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            logger.error("转换json失败",e);
        }
        return re;
    }
    //传入json字符串，转换为对象
    public <T> T StringToObj(String jsonStr,Class<T> tClass){
        T t=null;
        try {
            t = objectMapper.readValue(jsonStr,tClass);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            logger.error("json--->普通对象--失败!",e);
        }
        return t;
    }


    public static void main(String[] args) {
        ObjectUtils objectUtils = new ObjectUtils();
        User user=new User(1,"小白","fdasfda");
        String s = objectUtils.toJsonStr(user);
        User user1 = objectUtils.StringToObj(s, User.class);
        System.out.println(user1.getUserName());
    }
}
