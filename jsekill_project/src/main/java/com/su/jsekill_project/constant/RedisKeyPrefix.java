package com.su.jsekill_project.constant;

/**
 * @Classname RedisKeyPrefix
 * @author: 我心
 * @Description: redis中key的一些必要前缀
 * @Date 2023/1/23 12:33
 * @Created by Lenovo
 */
public class RedisKeyPrefix {
    //商品库存key的前缀
    public static final String GOODS_STOCK_PRE="GOODS_STOCK:";
    //session前缀
    public static final String SESSION_PRE="seckill_session:";
    //存储商品组的前缀
    public static final String GOODS_GROUP_PRE="seckill_group:";
    //秒杀成功的商品记录前缀
    public static final String GOODS_SUCCESS_PRE="goods_success:";
}
