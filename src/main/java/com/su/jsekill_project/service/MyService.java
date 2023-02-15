package com.su.jsekill_project.service;

import com.su.jsekill_project.mapper.SeckillGoodsMapper;
import com.su.jsekill_project.pojo.SeckillGoods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Classname MyService
 * @author: 我心
 * @Description:
 * @Date 2023/1/13 23:47
 * @Created by Lenovo
 */
@Service
public class MyService {
    @Autowired
    SeckillGoodsMapper seckillGoodsMapper;
    public int addSeckillGoods(SeckillGoods seckillGoods){
       //测试插入商品
//        int i = seckillGoodsMapper.insertSelective(seckillGoods);
        return 1;
    }
    //测试查询
    public SeckillGoods querySeckillGoodsById(int baseSeckillGoodsId){
        SeckillGoods seckillGoods = seckillGoodsMapper.querySeckillGoodsById(baseSeckillGoodsId);
        return seckillGoods;

    }
}
