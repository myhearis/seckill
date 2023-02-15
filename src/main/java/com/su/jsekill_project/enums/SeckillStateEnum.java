package com.su.jsekill_project.enums;

/**
 * @Classname SeckillStateEnum
 * @author: 我心
 * @Description: 关于订单处理状态的枚举类
 * @Date 2023/2/4 22:51
 * @Created by Lenovo
 */
public enum SeckillStateEnum {
    //消息发送到MQ时，MQ的ack超时，但是有可能消息已经发送成功了，用户可以等待或者重新发送请求
    MQ_Ack_TimeOut(10,"请等待，或者重新发送请求"),
    //请求正在校验
    Verify_Request(9,"校验请求中..."),
    //请求校验失败(库存不足，重复秒杀，秒杀时间未到等)
    Verify_Failed(8,"没抢到"),
    Verify_Request_Sucess(7,"请求校验成功，准备进入消息队列"),
    //请求进入了消息队列状态
    ENQUEUE_PRE_SECKILL(6, "排队中..."),

    /**
     * 释放分布式锁失败，秒杀被淘汰(如秒杀处理超时，分布式锁自动释放，还没处理完逻辑锁就已经超时自动释放)
     */
    DISTLOCK_RELEASE_FAILED(5, "没抢到"),

    /**
     * 获取分布式锁失败，秒杀被淘汰(如多个消费者同时消费的时候，多条消息被提取出来，如果没抢到分布式锁，就直接让请求失败，避免请求一直没有抢到锁占用资源的情况)
     */
    DISTLOCK_ACQUIRE_FAILED(4, "没抢到"),

    /**
     * Redis秒杀没抢到
     */

    REDIS_ERROR(3, "没抢到"),
    SOLD_OUT(2, "已售罄"),
    SUCCESS(1, "秒杀成功"),
    END(0, "秒杀结束"),
    REPEAT_KILL(-1, "重复秒杀"),

    /**
     * 运行时才能检测到的所有异常-系统异常
     */
    INNER_ERROR(-2, "没抢到"),

    /**
     * md5错误的数据篡改
     */
    DATA_REWRITE(-3, "数据篡改"),

    DB_CONCURRENCY_ERROR(-4, "没抢到"),
    /**
     * 被AccessLimitService限流了
     */
    ACCESS_LIMIT(-5, "没抢到");
    ;
    private int state;//状态码
    private String stateInfo;//状态描述

    SeckillStateEnum(int state, String stateInfo) {
        this.state = state;
        this.stateInfo = stateInfo;
    }

    SeckillStateEnum() {
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
    //通过状态码获取对应的枚举类对象,否则返回空
    public static SeckillStateEnum getSeckillState(int index){
        SeckillStateEnum[] values = values();
        for (SeckillStateEnum value : values) {
            if (value.getState()==index)
                return value;
        }
        return null;
    }
}
