package com.su.jsekill_project.pojo;

import java.util.Date;

/**
 * @Classname SeckillGoods
 * @author: 我心
 * @Description:秒杀商品
 * @Date 2023/1/12 23:13
 * @Created by Lenovo
 */
//跟数据库封装的
public class SeckillGoods {
    private int id;//商品id
    private String name;//商品名称
    private float price;//商品价格
    private String describe;//商品描述
    private int storage;//商品库存
    private int groupId;//商品组id

    public SeckillGoods() {
    }

    public SeckillGoods(String name, float price, String describe, int storage, int groupId) {
        this.name = name;
        this.price = price;
        this.describe = describe;
        this.storage = storage;
        this.groupId = groupId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public int getStorage() {
        return storage;
    }

    public void setStorage(int storage) {
        this.storage = storage;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    @Override
    public String toString() {
        return "SeckillGoods{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", describe='" + describe + '\'' +
                ", storage=" + storage +
                ", groupId=" + groupId +
                '}';
    }
}
