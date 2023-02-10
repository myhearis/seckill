package com.su.jsekill_project.pojo;

/**
 * @Classname SeckillRecordKey
 * @author: 我心
 * @Description: 秒杀记录的主键类
 * @Date 2023/1/16 22:06
 * @Created by Lenovo
 */
public class SeckillRecordKey {
    private int goodsId;
    private int groupId;
    private int userId;

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public int getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(int goodsId) {
        this.goodsId = goodsId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public SeckillRecordKey(int goodsId, int groupId, int userId) {
        this.goodsId = goodsId;
        this.groupId = groupId;
        this.userId = userId;
    }

    public SeckillRecordKey() {
    }

    @Override
    public String toString() {
        return "SeckillRecordKey{" +
                "goodsId=" + goodsId +
                ", groupId=" + groupId +
                ", userId=" + userId +
                '}';
    }
}
