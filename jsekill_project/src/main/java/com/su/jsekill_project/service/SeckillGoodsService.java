package com.su.jsekill_project.service;

import com.su.jsekill_project.dto.SeckillExecutionResult;
import com.su.jsekill_project.dto.SeckillMsgBody;
import com.su.jsekill_project.dto.SeckillResult;
import com.su.jsekill_project.exception.SeckillException;
import com.su.jsekill_project.pojo.SeckillGoods;
import com.su.jsekill_project.pojo.SeckillGoodsWrappings;
import org.apache.ibatis.annotations.Param;
import org.springframework.amqp.rabbit.connection.CorrelationData;

import java.util.List;

/**
 * @Classname SeckillGoodsService
 * @author: 我心
 * @Description:
 * @Date 2023/1/17 22:03
 * @Created by Lenovo
 */
public interface SeckillGoodsService {
//    添加商品
    int addSeckillGoods(SeckillGoods seckillGoods);
//    删除商品
    int deleteSeckillGoods(int seckillGoodsId);
//    查询商品
    SeckillGoods getSeckillGoodsById(int seckillGoodsId);
//    查询商品（使用商品包装类）
    SeckillGoodsWrappings getSeckillGoodsWrappingsById(int seckillGoodsId);
//    更改商品
    int updateSeckillGoods(SeckillGoods seckillGoods);
//  查询所有商品
    List<SeckillGoods> getSeckillGoodsList();
//判断一个商品是否开启秒杀,传入格式化后的字符串
    boolean isSeckill(String startTime,String endTime);
    //预加载一组商品到redis中
    void  loadSeckillGoodsToRedis(List<SeckillGoods> goodsList);
    //加载所有商品组到redis中
    void  loadSeckillGoodsGroupsToRedis(List<List<SeckillGoods>> goodsGroups);
    //优先从redis中加载商品，否则从数据库中加载，并缓存到redis中
    SeckillGoods getSeckillGoodsToRedisAndDataBase(int goodsId,int groupId);
    //从redis中通过key获取一个商品
    SeckillGoods getSeckillByRedis(String key);
    //通过组去查询商品
    List<SeckillGoods> getSeckillGoodsByGroupId(int groupId);
    //从redis中获取秒杀商品列表
    List<SeckillGoods> getSeckillGoodsByRedis();
    //从redis中获取商品库存
    int getStorageToRedis(int goodsId,int groupId);
    //减库存操作
    int decrStorage( int goodsId,  int groupId);
    //秒杀处理方法,返回秒杀结果
    SeckillExecutionResult seckillProcess(int goodsId, int groupId, int userId);
    //判断前端的秒杀请求是否有效
    boolean verifySeckillRequest(int goodsId, int groupId, int userId, SeckillExecutionResult seckillExecutionResult);
    //生成记录进入mq的set集合中的键
    String enterMqGoodsKey(int goodsId,int groupId,int userId);
    //封装发给MQ的秒杀请求
    SeckillMsgBody createSeckillMsgBody(int goodsId,int groupId,int userId);
    //用于推送消息后的异步回调方法
    void confirmCallback(CorrelationData correlationData, boolean ack, String cause);
    //消费者真正秒杀的方法
    void redisSeckillHandler(int userId,int goodsId,int groupId) throws SeckillException;
    //redis秒杀成功后，修改数据库操作
    SeckillExecutionResult updateStorageAndRecord(int userId,int goodsId,int groupId);
}
