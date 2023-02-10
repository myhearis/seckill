package com.su.jsekill_project.pojo;

/**
 * @Classname SeckillRecord
 * @author: 我心
 * @Description: 秒杀记录
 * @Date 2023/1/13 22:50
 * @Created by Lenovo
 */
public class SeckillRecord {
    private int goodsId;
    private int groupId;
    private int userId;
    //前面三个，是记录的主键
    private long seckillTime;

    public SeckillRecord(int goodsId, int groupId, int userId, long seckillTime) {
        this.goodsId = goodsId;
        this.groupId = groupId;
        this.userId = userId;
        this.seckillTime = seckillTime;
    }
    //初始化主键
    public void initKey(SeckillRecordKey recordKey){
        this.goodsId=recordKey.getGoodsId();
        this.groupId=recordKey.getGroupId();
        this.userId=recordKey.getUserId();
    }
    public SeckillRecord() {
    }

    public int getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(int goodsId) {
        this.goodsId = goodsId;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public long getSeckillTime() {
        return seckillTime;
    }

    @Override
    public String toString() {
        return "SeckillRecord{" +
                "goodsId=" + goodsId +
                ", groupId=" + groupId +
                ", userId=" + userId +
                ", seckillTime=" + seckillTime +
                '}';
    }

    public void setSeckillTime(long seckillTime) {
        this.seckillTime = seckillTime;
    }
}
