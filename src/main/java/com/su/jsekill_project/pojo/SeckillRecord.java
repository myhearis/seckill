package com.su.jsekill_project.pojo;

/**
 * @Classname SeckillRecord
 * @author: 我心
 * @Description: 秒杀记录
 * @Date 2023/1/13 22:50
 * @Created by Lenovo
 */
public class SeckillRecord {
    //复合主键对象
    private SeckillRecordKey key;
    //秒杀成功的时间，字符串
    private String successTime;

    public SeckillRecordKey getKey() {
        return key;
    }

    public void setKey(SeckillRecordKey key) {
        this.key = key;
    }

    public String getSuccessTime() {
        return successTime;
    }

    public void setSuccessTime(String successTime) {
        this.successTime = successTime;
    }

    public SeckillRecord(SeckillRecordKey key, String successTime) {
        this.key = key;
        this.successTime = successTime;
    }

    public SeckillRecord() {

    }
}
