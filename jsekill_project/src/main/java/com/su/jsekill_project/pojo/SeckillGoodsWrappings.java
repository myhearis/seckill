package com.su.jsekill_project.pojo;

import com.su.jsekill_project.Utils.DateUtil;

import java.util.Date;

/**
 * @Classname SeckillGoodsWrappings
 * @author: 我心
 * @Description: SeckillGoods的包装类，用于转化日期，进行前台的交互
 * @Date 2023/1/13 22:15
 * @Created by Lenovo
 */
public class SeckillGoodsWrappings {
//    private DateUtil dateUtil=new DateUtil();//工具类
//    private SeckillGoods seckillGoods;
//    private long endTime;//结束时间的毫秒数
//    private long startTime;//开始时间的毫秒数
//    public int getId() {
//        return seckillGoods.getId();
//    }
//
//    public void setId(int id) {
//        seckillGoods.setId(id);
//    }
//
//    public String getName() {
//        return seckillGoods.getName();
//    }
//
//    public void setName(String name) {
//        seckillGoods.setName(name);
//    }
//
//    public float getPrice() {
//        return seckillGoods.getPrice();
//    }
//
//    public void setPrice(float price) {
//        seckillGoods.setPrice(price);
//    }
//    //返回毫秒数
//    public long getStart() {
//        return startTime;
//    }
//
//    public void setStart(String start) {
//        //先更新原生商品对象的内容
//        seckillGoods.setStart(start);
//        //再更新当前包装类的内容
//        startTime=dateUtil.parse(start).getTime();
//    }
//
//    public long getEnd() {
//        return endTime;
//    }
//
//    public void setEnd(String end) {
//        //设置原生对象的结束时间字符串
//        seckillGoods.setEnd(end);
//        //设置包装对象的毫秒数
//        endTime=dateUtil.parse(end).getTime();
//    }
//
//    public String getDescribe() {
//        return seckillGoods.getDescribe();
//    }
//
//    public void setDescribe(String describe) {
//        seckillGoods.setDescribe(describe);
//    }
//
//    public SeckillGoods getSeckillGoods() {
//        return seckillGoods;
//    }
//    private void init(){
//        startTime=dateUtil.parse(seckillGoods.getStart()).getTime();
//        endTime=dateUtil.parse(seckillGoods.getEnd()).getTime();
//    }
//    //设置一次原生商品，就进行一次初始化操作
//    public void setSeckillGoods(SeckillGoods seckillGoods) {
//        this.seckillGoods = seckillGoods;
//        init();
//    }
//
//    public SeckillGoodsWrappings(SeckillGoods seckillGoods) {
//        this.seckillGoods = seckillGoods;
//        init();
//    }
//
//    public SeckillGoodsWrappings() {
//
//    }
//
//    public static void main(String[] args) {
//        //创建一个秒杀商品YYYY-MM-dd HH:mm:ss:SSS
//        SeckillGoods seckillGoods=new SeckillGoods(1,"手机", 100, "2023-01-10 22:30:00:000", "2023-01-10 23:30:00:000", "测试商品");
//        //创建商品包装类
//        SeckillGoodsWrappings seckillGoodsWrappings=new SeckillGoodsWrappings(seckillGoods);
//        System.out.println(seckillGoodsWrappings.getName());
//        long end = seckillGoodsWrappings.getEnd();
//        Date date=new Date(end);
//        System.out.println(date);
//    }
}
