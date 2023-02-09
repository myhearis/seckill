package com.su.jsekill_project.dto;

import java.io.Serializable;

/**
 * @Classname SeckillMsgBody
 * @author: 我心
 * @Description: 秒杀信息封装类，只需要记录用户id和秒杀商品id即可
 * @Date 2023/1/18 12:45
 * @Created by Lenovo
 */
public class SeckillMsgBody implements Serializable {
    //序列化号
    private static final long serialVersionUID = -4206751408398568444L;
//    用户id
    private int userId;
//    商品id
    private int goodsId;
    //商品组id
    private int groupId;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(int goodsId) {
        this.goodsId = goodsId;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public SeckillMsgBody(int userId, int goodsId, int groupId) {
        this.userId = userId;
        this.goodsId = goodsId;
        this.groupId = groupId;
    }

    public SeckillMsgBody() {
    }
}
