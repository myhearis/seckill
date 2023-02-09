package com.su.jsekill_project.service;

import com.su.jsekill_project.pojo.SeckillGoods;
import com.su.jsekill_project.pojo.SeckillGoodsGroup;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @Classname SeckillGoodsGroupService
 * @author: 我心
 * @Description: 商品组业务接口
 * @Date 2023/1/26 22:33
 * @Created by Lenovo
 */

public interface SeckillGoodsGroupService {
    //    插入商品组
    int insertSeckillGroup(@Param("seckillGoodsGroup") SeckillGoodsGroup seckillGoodsGroup);
    //    删除商品组
    int deleteSeckillGroupById(@Param("groupId") int groupId);
    //    查询商品组
    SeckillGoodsGroup querySeckillGoodsGroupById(@Param("groupId") int groupId);
    //    查询商品组，同时加载组内的商品集合
    SeckillGoodsGroup querySeckillGoodsGroupByIdAndGoodList(@Param("groupId") int groupId);
    //    查询所有商品组，以及所有商品组内的商品集合
    List<SeckillGoods> queryAllGroup();
    //    更新商品组
    int updateSeckillGoodsGroup(@Param("seckillGoodsGroup") SeckillGoodsGroup seckillGoodsGroup);
    //将所有商品组加载到redis中
    void loadAllGroupToRedis();
    //将一个商品组加载到redis中
    void loadOneGroupToRedis(SeckillGoodsGroup group);
    //查询商品组，但不加载商品集合，将商品组的json对象加载到redis中
    SeckillGoodsGroup queryGroupAndLoadRedis(int groupId);
    //从redis中获取一个商品组，如果不存在，则返回null
    SeckillGoodsGroup getSeckillGroupByRedis(int groupId);
    //判断是否开启秒杀,传入商品组对象和当前时间
    boolean isSeckill(SeckillGoodsGroup group, Date date);
}
