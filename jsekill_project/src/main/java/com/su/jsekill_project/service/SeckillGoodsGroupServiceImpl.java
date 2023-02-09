package com.su.jsekill_project.service;

import com.google.gson.Gson;
import com.su.jsekill_project.Utils.DateUtil;
import com.su.jsekill_project.constant.RedisKey;
import com.su.jsekill_project.constant.RedisKeyPrefix;
import com.su.jsekill_project.mapper.SeckillGoodsGroupMapper;
import com.su.jsekill_project.pojo.SeckillGoods;
import com.su.jsekill_project.pojo.SeckillGoodsGroup;
import com.su.jsekill_project.type.SeckillGoodsGroupType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @Classname SeckillGoodsGroupServiceImpl
 * @author: 我心
 * @Description:
 * @Date 2023/1/26 22:47
 * @Created by Lenovo
 */
@Service
public class SeckillGoodsGroupServiceImpl implements SeckillGoodsGroupService{
    @Autowired
    private SeckillGoodsGroupMapper groupMapper;
    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    private Gson gson;
    @Autowired
    private DateUtil dateUtil;
    @Override
    public int insertSeckillGroup(SeckillGoodsGroup seckillGoodsGroup) {
        return groupMapper.insertSeckillGroup(seckillGoodsGroup);
    }

    @Override
    public int deleteSeckillGroupById(int groupId) {
        return groupMapper.deleteSeckillGroupById(groupId);
    }

    @Override
    public SeckillGoodsGroup querySeckillGoodsGroupById(int groupId) {
        return groupMapper.querySeckillGoodsGroupById(groupId);
    }

    @Override
    public SeckillGoodsGroup querySeckillGoodsGroupByIdAndGoodList(int groupId) {
        return groupMapper.querySeckillGoodsGroupByIdAndGoodList(groupId);
    }

    @Override
    public List<SeckillGoods> queryAllGroup() {
        return groupMapper.queryAllGroup();
    }

    @Override
    public int updateSeckillGoodsGroup(SeckillGoodsGroup seckillGoodsGroup) {
        return groupMapper.updateSeckillGoodsGroup(seckillGoodsGroup);
    }
    //加载所有的商品组信息到redis中
    @Override
    public void loadAllGroupToRedis() {
        //获取所有商品组集合
        List<SeckillGoods> goodsList = groupMapper.queryAllGroup();
        //将商品集合对象转化为json字符串
        Gson gson=new Gson();
        String s = gson.toJson(goodsList);
        //存入redis中
        redisTemplate.opsForValue().set(RedisKey.SECKILL_GOODS_KEY,s);
    }
    //加载一个商品组到redis中
    @Override
    public void loadOneGroupToRedis(SeckillGoodsGroup group) {
        //转化成json字符串
        String s = gson.toJson(group);
        //生成key
        String key = goodsGroupRedisKey(group);
        //存入redis中
        redisTemplate.opsForValue().set(key,s);
    }

    // 查询商品组，优先从redis中读取，否则从数据库中读取,并加载到redis中(一般用于从redis中获取商品组的秒杀时间)
    @Override
    public SeckillGoodsGroup queryGroupAndLoadRedis(int groupId) {
        //需要返回的内容
        SeckillGoodsGroup seckillGoodsGroup = null;
        //判断商品组是否存在于redis中
        SeckillGoodsGroup redisGroup = getSeckillGroupByRedis(groupId);
        //如果在
        if (redisGroup!=null){
            seckillGoodsGroup=redisGroup;
        }
        //如果不在，则从数据库中加载,并加载到redis中
        else {
            seckillGoodsGroup=querySeckillGoodsGroupById(groupId);
            //加载到redis中
            if (seckillGoodsGroup!=null){
                loadOneGroupToRedis(seckillGoodsGroup);
            }
        }


        return seckillGoodsGroup;
    }


    //从redis中获取商品组,不存在则返回null
    @Override
    public SeckillGoodsGroup getSeckillGroupByRedis(int groupId) {
        SeckillGoodsGroup group=null;//要返回的结果
        //获取key
        String key = goodsGroupRedisKey(groupId);
        //从redis中获取字符串
        String s = redisTemplate.opsForValue().get(key);
        //如果取出的value不为null且长度不为0，说明存在
        if (s!=null&&s.length()!=0){
            //将json转化为组对象
            Object o = gson.fromJson(s, new SeckillGoodsGroupType().getType());
            if (o instanceof SeckillGoodsGroup){
                group=(SeckillGoodsGroup) o;
            }

        }
        return group;
    }
    //判断秒杀是否开始
    @Override
    public boolean isSeckill(SeckillGoodsGroup group, Date date) {
        //获取秒杀时间的date对象
        Date startTime = new Date(group.getStart());
        Date endTime = new Date(group.getEnd());
        //判断当前时间是否在秒杀时间段以内(即时间轴上，当前时间在开始时间后面，在结束时间前面)
        if (date.after(startTime)&&date.before(endTime)){
            return true;
        }

        return false;
    }


    //生成该商品组的redis中的key
    private String goodsGroupRedisKey(SeckillGoodsGroup seckillGoodsGroup){
        return goodsGroupRedisKey(seckillGoodsGroup.getGroupId());
    }


    //通过组id生成商品组的key:  前缀+组id
    private String goodsGroupRedisKey(int groupId){
        StringBuffer key=new StringBuffer();
        key.append(RedisKeyPrefix.GOODS_GROUP_PRE);
        key.append(groupId);//添加id
        return key.toString();
    }
}
