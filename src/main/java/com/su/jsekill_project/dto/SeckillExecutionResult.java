package com.su.jsekill_project.dto;

import com.su.jsekill_project.pojo.SeckillRecord;

/**
 * @Classname SeckillExecutionResult
 * @author: 我心
 * @Description: 秒杀的执行结果封装对象
 * @Date 2023/1/18 12:50
 * @Created by Lenovo
 */
public class SeckillExecutionResult {
//    秒杀商品id
    private int goodsId;
    private int groupId;

//    秒杀执行结果状态
    private int state;
//    状态描述
    private String stateInfo;
//    秒杀成功后生成的订单对象
    private SeckillRecord seckillRecord;

    public int getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(int goodsId) {
        this.goodsId = goodsId;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public void setStateInfo(String stateInfo) {
        this.stateInfo = stateInfo;
    }

    public SeckillRecord getSeckillRecord() {
        return seckillRecord;
    }

    public void setSeckillRecord(SeckillRecord seckillRecord) {
        this.seckillRecord = seckillRecord;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }
}
