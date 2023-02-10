package com.su.jsekill_project.customer;

import com.su.jsekill_project.dto.SeckillMsgBody;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @Classname SeckillReceiver
 * @author: 我心
 * @Description: 用于消费mq中秒杀队列的消息
 * @Date 2023/2/9 14:15
 * @Created by Lenovo
 */
@Component
@RabbitListener(queues = "seckill_queue")
public class SeckillReceiver {
    //开始消费秒杀请求
    @RabbitHandler
    public  void  process(SeckillMsgBody msgBody){
        //处理消息
        //1.在redis中进行秒杀操作
        //2.在数据库中更新
        //捕获异常，并处理
        //手动回复ack
        //将成功的消息再次写入redis中的 enterMQ记录中，避免超时，没有记录但是发送到了mq的情况

        System.out.println("接收到消息"+msgBody);
    }
}
