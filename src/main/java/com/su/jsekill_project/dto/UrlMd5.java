package com.su.jsekill_project.dto;

/**
 * @Classname UrlMd5
 * @author: 我心
 * @Description: 用于给前台秒杀请求加签处理，并传秒杀时间给用户，以及是否开启秒杀
 * @Date 2023/2/14 11:25
 * @Created by Lenovo
 */
public class UrlMd5 {
    private String md5;
    private long startTime;//商品的秒杀开始时间
    private long endTime;//商品秒杀的结束时间
    private long nowTime;//当前服务器时间
    private boolean openKill;//判断是否开启秒杀

    public UrlMd5() {
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

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

    public long getNowTime() {
        return nowTime;
    }

    public void setNowTime(long nowTime) {
        this.nowTime = nowTime;
    }

    public boolean isOpenKill() {
        return openKill;
    }

    public void setOpenKill(boolean openKill) {
        this.openKill = openKill;
    }
}
