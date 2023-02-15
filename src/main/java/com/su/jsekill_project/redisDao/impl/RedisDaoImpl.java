package com.su.jsekill_project.redisDao.impl;

import com.google.gson.Gson;
import com.su.jsekill_project.constant.RedisKey;
import com.su.jsekill_project.constant.RedisKeyPrefix;
import com.su.jsekill_project.pojo.SeckillGoods;
import com.su.jsekill_project.pojo.SeckillGoodsGroup;
import com.su.jsekill_project.redisDao.RedisDao;
import com.su.jsekill_project.type.SeckillGoodsGroupType;
import com.su.jsekill_project.type.SeckillGoodsListType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Classname RedisDaoImpl
 * @author: 我心
 * @Description:
 * @Date 2023/2/6 20:29
 * @Created by Lenovo
 */
@Component
public class RedisDaoImpl implements RedisDao {
    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    private Gson gson;
    @Override
    public String enterMQValue(int userId, int goodsId, int groupId) {
        StringBuffer stringBuffer=new StringBuffer();
        stringBuffer.append(userId);
        stringBuffer.append('@');
        stringBuffer.append(groupId);
        stringBuffer.append('@');
        stringBuffer.append(goodsId);
        return stringBuffer.toString();

    }

    @Override
    public String storageKey(int goodsId, int groupId) {
        StringBuffer stringBuffer=new StringBuffer();
        stringBuffer.append(RedisKeyPrefix.GOODS_STOCK_PRE);//key前缀
        stringBuffer.append("-");
        stringBuffer.append(groupId);//组id
        stringBuffer.append("-");
        stringBuffer.append(goodsId);//商品id
        return stringBuffer.toString();
    }

    @Override
    public String goodsGroupKey(int groupId) {
        StringBuffer key=new StringBuffer();
        key.append(RedisKeyPrefix.GOODS_GROUP_PRE);
        key.append(groupId);//添加id
        return key.toString();
    }
    //生成商品的秒杀成功集合的key (前缀@组id@商品id)
    @Override
    public String seckillSuccessKey(int goodsId, int groupId) {
        StringBuffer key=new StringBuffer();
        key.append(RedisKeyPrefix.GOODS_SUCCESS_PRE);
        key.append('@');
        key.append(groupId);//组id
        key.append('@');
        key.append(goodsId);//商品id
        return key.toString();
    }


    //通过组id从redis中查询一个商品组
    @Override
    public SeckillGoodsGroup getGoodsGroup(int groupId) {
        SeckillGoodsGroup re=null;
        //生成key
        String groupKey=goodsGroupKey(groupId);
        //查询
        String s = redisTemplate.opsForValue().get(groupKey);
        //转化成为java对象
        Object o = gson.fromJson(s, new SeckillGoodsGroupType().getType());
        if (o instanceof SeckillGoodsGroup)
            re= (SeckillGoodsGroup) o;
        return re;
    }
    //从redis中获取商品列表,如果没有则返回null
    @Override
    public List<SeckillGoods> getSeckillGoodsList() {
        List<SeckillGoods> goodsList=null;
        //商品列表的key
        String s = redisTemplate.opsForValue().get(RedisKey.SECKILL_GOODS_KEY);
        //转化为java对象
        if (s!=null&&s.length()>0){
            Object o = gson.fromJson(s, new SeckillGoodsListType().getType());
            //强转为对应的类型
            goodsList= (List<SeckillGoods>) o;
        }
        return goodsList;
    }

    @Override
    public Long enterMqRequest(int goodsId, int groupId, int userId) {
        //生成value
        String value = enterMQValue(userId,goodsId,groupId );
        Long add = redisTemplate.opsForSet().add(RedisKey.ENTER_SECKIL_MQ_KEY, value);
        return add;
    }

    @Override
    public boolean isEnterMQ(int goodsId, int groupId, int userId) {
        String value = enterMQValue(userId, goodsId, groupId);
        return redisTemplate.opsForSet().isMember(RedisKey.ENTER_SECKIL_MQ_KEY,value);
    }
    //记录用户秒杀成功到redis中
    @Override
    public Long userSeckillSuccessRecord(int goodsId, int groupId, int userId) {
        //生成set集合的key
        String setKey = seckillSuccessKey(goodsId, groupId);
        //直接将用户id作为value记录进入即可
        return redisTemplate.opsForSet().add(setKey,String.valueOf(userId));
    }
}
