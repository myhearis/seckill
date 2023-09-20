package com.su.jsekill_project.constant;

/**
 * @Classname RedisKey
 * @author: 我心
 * @Description: 记录了redis中一些信息的key
 * @Date 2023/1/23 12:26
 * @Created by Lenovo
 */
public class RedisKey {
    //秒杀成功队列的key，类型是set
    public static final String SECKILL_SUCCESS_KEY="SECKILL_SUCCESS";
    //进入秒杀队列的记录key，类型是set
    public static final String ENTER_SECKIL_MQ_KEY="ENTER_SECKIL_MQ";
    //商品预加载到redis中的key,类型是set
    public static final String SECKILL_GOODS_KEY="SECKILL_GOODS";

}
