package com.su.jsekill_project.mapper;

import com.su.jsekill_project.pojo.SeckillGoods;
import com.su.jsekill_project.pojo.SeckillGoodsGroup;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Classname SeckillGoodsGroup
 * @author: 我心
 * @Description: 秒杀商品组dao接口
 * @Date 2023/1/20 11:35
 * @Created by Lenovo
 */
@Mapper
public interface SeckillGoodsGroupMapper {
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

}
