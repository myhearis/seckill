package com.su.jsekill_project.mapper;
import com.su.jsekill_project.pojo.SeckillGoods;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SeckillGoodsMapper {
    //添加秒杀商品
    int insertSeckillGoods(@Param("seckillGoods") SeckillGoods seckillGoods);
    //批量添加商品
    int insertSeckillGoodsList(@Param("seckillGoodsList") List<SeckillGoods> seckillGoodsList);
    //通过id查询秒杀商品
    SeckillGoods querySeckillGoodsById(@Param("seckillGoodsId") int seckillGoodsId);
    //查询所有秒杀商品
    List<SeckillGoods> queryAllSeckillGoods();
    //分页查询秒杀商品
    //int startIndex:起始索引,int len:分页大小
    List<SeckillGoods> querySeckillGoodsPage(@Param("startIndex")int startIndex,@Param("len") int len);
    //通过商品组id去查询同一组的商品
    List<SeckillGoods> querySeckillGoodsListByGroupId(@Param("groupId") int groupId);
    //更新秒杀商品
    int updateSeckillGoods(@Param("seckillGoods") SeckillGoods seckillGoods);
    //删除秒杀商品
    int deleteSeckillGoodsById(@Param("seckillGoodsId") int seckillGoodsId);
    //减库存操作
    int decrStorage(@Param("goodsId") int goodsId,@Param("groupId") int groupId);
    //查询商品并查询商品组(这里使用分步查询)
    SeckillGoods getSeckillGoodsAndGroup(@Param("goodsId") int goodsId);

}