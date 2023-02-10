package com.su.jsekill_project.redisDao;

import com.su.jsekill_project.pojo.SeckillGoods;
import com.su.jsekill_project.pojo.SeckillGoodsGroup;

import java.util.List;

/**
 * @Classname RedisDao
 * @author: 我心
 * @Description: 操作redis的dao接口
 * @Date 2023/2/6 20:25
 * @Created by Lenovo
 */
public interface RedisDao {
    //生成redis中进入消息队列的value
    String enterMQValue(int userId,int goodsId,int groupId);
    //生成库存key
    String storageKey(int goodsId,int groupId);
    //生成存储redis组的key
    String goodsGroupKey(int groupId);
    //生成秒杀成功的商品记录的set集合key
    String seckillSuccessKey(int goodsId,int groupId);
    //生成秒杀成功的商品记录的value
//    String seckillSuccessValue(int goodsId,int groupId,int userId);
    //从redis中获取一个商品组
    SeckillGoodsGroup getGoodsGroup(int groupId);
    //从redis中获取商品列表
    List<SeckillGoods> getSeckillGoodsList();
    //记录成功进入队列的请求到redis中,返回影响的个数
    Long enterMqRequest(int goodsId, int groupId, int userId);
    //判断当前请求是否已经进入了mq中（在redis中记录的，但不一定准确，因为有可能ack超时没有记录到redis中，但是却进入了mq）
    boolean isEnterMQ(int goodsId,int groupId,int userId);
    //用户秒杀成功指定商品的记录
    Long userSeckillSuccessRecord(int goodsId,int groupId,int userId);
    //判断当前用户是否已经秒杀成功过当前商品
    boolean isSeckillSuccessGoods(int goodsId,int groupId,int userId);
    //从redis中获取指定商品的库存，如果不存在则返回-1
    int getSeckillGoodsStorage(int goodsId,int groupId);
}
