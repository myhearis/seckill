package com.su.jsekill_project.bean;

/**
 * @Classname SeckillRemainTime
 * @author: 我心
 * @Description: 秒杀剩余时间类
 * @Date 2023/1/28 22:49
 * @Created by Lenovo
 */
public class SeckillRemainTime {

    private long hour;
    private long minute;
    private long second;

    public long getHour() {
        return hour;
    }

    public void setHour(long hour) {
        this.hour = hour;
    }

    public long getMinute() {
        return minute;
    }

    public void setMinute(long minute) {
        this.minute = minute;
    }

    public long getSecond() {
        return second;
    }

    public void setSecond(long second) {
        this.second = second;
    }
}
