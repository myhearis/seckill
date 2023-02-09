package com.su.jsekill_project.pojo;

/**
 * @Classname SeckillTime
 * @author: 我心
 * @Description: 秒杀时间段的对象
 * @Date 2023/1/29 22:12
 * @Created by Lenovo
 */
public class SeckillTime {
    private long startTime;
    private long endTime;

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public SeckillTime(long startTime, long endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public SeckillTime() {
    }
}
