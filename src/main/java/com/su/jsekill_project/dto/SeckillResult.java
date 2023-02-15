package com.su.jsekill_project.dto;

/**
 * @Classname SeckillResult
 * @author: 我心
 * @Description: 返回给浏览器的json数据格式,泛型用于定义数据的类型
 * @Date 2023/1/18 22:29
 * @Created by Lenovo
 */
public class SeckillResult<T> {
    private boolean isSeckill;//是否开启秒杀
    private T data;//封装的数据
    private String error;//如果发生了错误，对错误的描述

    public boolean isSeckill() {
        return isSeckill;
    }

    public void setSeckill(boolean seckill) {
        isSeckill = seckill;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
