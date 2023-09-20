package com.su.jsekill_project.enums;

/**
 * @Classname SeckillAckEnum
 * @author: 我心
 * @Description: 消费者响应的ack类
 * @Date 2023/2/11 11:30
 * @Created by Lenovo
 */
public  enum SeckillAckEnum {
    //秒杀成功ack
    ACCEPT,
    //出现其他异常，需要重新入队ack
    RETRY,
    //秒杀不成功，忽略并确认(由于重复秒杀，或者不在秒杀时段内，库存不足等)
    THROW
}
