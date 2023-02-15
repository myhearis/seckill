package com.su.jsekill_project.pojo;

import java.util.List;

/**
 * @Classname SeckillGoodsGroup
 * @author: 我心
 * @Description: 秒杀商品组
 * @Date 2023/1/20 11:23
 * @Created by Lenovo
 */
public class SeckillGoodsGroup {
    private int groupId;//组id
    private long start;//商品开始时间的毫秒数
    private long end;//商品结束时间的毫秒数
    private List<SeckillGoods> goodsList;//当前组的秒杀商品

    public SeckillGoodsGroup() {
    }

    public SeckillGoodsGroup(long start, long end) {
        this.start = start;
        this.end = end;
    }

    public SeckillGoodsGroup(int groupId, long start, long end, List<SeckillGoods> goodsList) {
        this.groupId = groupId;
        this.start = start;
        this.end = end;
        this.goodsList = goodsList;
    }

    public SeckillGoodsGroup(long start, long end, List<SeckillGoods> goodsList) {
        this.start = start;
        this.end = end;
        this.goodsList = goodsList;
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
