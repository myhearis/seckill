package com.su.jsekill_project.pojo;

import java.util.List;

/**
 * @Classname BaseSeckillGroup
 * @author: 我心
 * @Description: 基本的商品组类，用于封装数据与数据库对接
 * @Date 2023/1/30 12:17
 * @Created by Lenovo
 */
public class BaseSeckillGoodsGroup {
    private int groupId;//组id
    private long start;//商品开始时间的毫秒数
    private long end;//商品结束时间的毫秒数
    private List<SeckillGoods> goodsList;//当前组的秒杀商品

    public BaseSeckillGoodsGroup() {

    }

    public BaseSeckillGoodsGroup(long start, long end, List<SeckillGoods> goodsList) {
        this.start = start;
        this.end = end;
        this.goodsList = goodsList;
    }

    public BaseSeckillGoodsGroup(long start, long end) {
        this.start = start;
        this.end = end;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public long getStart() {
        return start;
    }

    public void setStart(long start) {
        this.start = start;
    }

    public long getEnd() {
        return end;
    }

    public void setEnd(long end) {
        this.end = end;
    }

    public List<SeckillGoods> getGoodsList() {
        return goodsList;
    }

    public void setGoodsList(List<SeckillGoods> goodsList) {
        this.goodsList = goodsList;
    }
}
