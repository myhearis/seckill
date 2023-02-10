package com.su.jsekill_project.exception;

import com.su.jsekill_project.enums.SeckillStateEnum;

/**
 * @Classname SeckillException
 * @author: 我心
 * @Description: 秒杀相关异常
 * @Date 2023/2/9 21:29
 * @Created by Lenovo
 */
public class SeckillException extends RuntimeException{
    //状态枚举类
    private SeckillStateEnum stateEnum;

    public SeckillStateEnum getStateEnum() {
        return stateEnum;
    }

    public void setStateEnum(SeckillStateEnum stateEnum) {
        this.stateEnum = stateEnum;
    }

    public SeckillException(SeckillStateEnum stateEnum) {
        this.stateEnum = stateEnum;
    }

    public SeckillException(String message, SeckillStateEnum stateEnum) {
        super(message);
        this.stateEnum = stateEnum;
    }
    public SeckillException(String message){
        super(message);
    }
}
