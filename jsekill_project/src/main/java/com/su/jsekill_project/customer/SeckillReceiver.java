package com.su.jsekill_project.customer;

import com.google.gson.Gson;
import com.rabbitmq.client.Channel;
import com.su.jsekill_project.dto.SeckillExecutionResult;
import com.su.jsekill_project.dto.SeckillMsgBody;
import com.su.jsekill_project.enums.SeckillAckEnum;
import com.su.jsekill_project.enums.SeckillStateEnum;
import com.su.jsekill_project.exception.SeckillException;
import com.su.jsekill_project.redisDao.RedisDao;
import com.su.jsekill_project.service.SeckillGoodsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;


/**
 * @Classname SeckillReceiver
 * @author: 我心
 * @Description: 用于消费mq中秒杀队列的消息
 * @Date 2023/2/9 14:15
 * @Created by Lenovo
 */
@Component
//@RabbitListener(queues = "seckill_queue")
public class SeckillReceiver implements ChannelAwareMessageListener {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private SeckillGoodsService goodsService;
    @Autowired
    private RedisDao redisDao;
    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private Gson gson;
    //开始消费秒杀请求
    public  void  process(SeckillMsgBody msgBody){
        //处理消息
        SeckillExecutionResult executionResult=null;
        //获取信息
        int userId = msgBody.getUserId();
        int goodsId = msgBody.getGoodsId();
        int groupId = msgBody.getGroupId();
        //确认状态
        SeckillAckEnum ackEnum=SeckillAckEnum.ACCEPT;//默认为接受确认
        //捕获异常，并处理
        try {
            //1.在redis中进行秒杀操作
            goodsService.redisSeckillHandler(userId,goodsId,groupId);
            //2.在数据库中更新
            executionResult = goodsService.updateStorageAndRecord(userId, goodsId, groupId);
        } catch (SeckillException e) {
            e.printStackTrace();
            //如果是其他异常，则设置ack为重新入队
            if (e.getStateEnum()== SeckillStateEnum.INNER_ERROR){
                ackEnum=SeckillAckEnum.RETRY;
            }
            //如果是秒杀的异常，则回滚,抛弃消息
            else {
                ackEnum=SeckillAckEnum.THROW;
            }
        }
        //手动回复ack
        finally {
            switch (ackEnum){
                case ACCEPT:
                    break;
                case THROW:
                    break;
                case RETRY:
                    break;
            }
        }

        //将成功的消息再次写入redis中的 enterMQ记录中，避免超时，没有记录但是发送到了mq的情况

        System.out.println("接收到消息"+msgBody);
    }

    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        //获取消息的id
        long deliveryTag = message.getMessageProperties().getDeliveryTag();
        //因为传递消息时是使用map封装的，所以将Map从Message中取出来需要做一些处理
        String msg = message.toString();
        String[] msgList = msg.split("'");
        String bodyStr = msgList[1];//将body的内容提取出来
        //将消息的json字符串转化为java对象
        SeckillMsgBody msgBody = gson.fromJson(bodyStr, SeckillMsgBody.class);
        SeckillAckEnum ackEnum=SeckillAckEnum.ACCEPT;//默认为接受确认ack
        try {
            //1.在redis中进行秒杀操作
            goodsService.redisSeckillHandler(msgBody.getUserId(),msgBody.getGoodsId(), msgBody.getGroupId());
            //2.更新数据库
            SeckillExecutionResult executionResult = goodsService.updateStorageAndRecord(msgBody.getUserId(), msgBody.getGoodsId(), msgBody.getGroupId());


        }
        //捕获异常异常，捕获到异常
        catch (SeckillException e) {
            e.printStackTrace();
            //如果是其他异常，则将请求重新入队，设置状态
            if (e.getStateEnum()==SeckillStateEnum.INNER_ERROR){
                ackEnum=SeckillAckEnum.RETRY;
            }
            //如果是秒杀系统异常，则忽略消息（抛出的异常可能是秒杀时间已过，商品已售磐等）
            else {
                ackEnum=SeckillAckEnum.THROW;
            }
        }
        finally {
            switch (ackEnum){
                //接受消息，并确认
                case ACCEPT:
                    logger.info("秒杀成功!");
                    //1.确认
                    //手动回复ack
                    channel.basicAck(deliveryTag,false);
                    //2.再次记录到redis中，防止ack超时没有记录但却处理了请求
                    redisDao.enterMqRequest(msgBody.getGoodsId(), msgBody.getGroupId(), msgBody.getUserId());
                    break;
                case THROW:
                    logger.info("重复秒杀，或者已售磐等---秒杀失败,请求被抛弃");
                    channel.basicAck(deliveryTag,false);
                    //记录到redis中
                    redisDao.enterMqRequest(msgBody.getGoodsId(), msgBody.getGroupId(), msgBody.getUserId());
                    break;
                case RETRY:
                    logger.info("系统出现错误，已经将请求重新入队");
                    //确认并重新入队
                    channel.basicAck(deliveryTag,true);
                    //这里没有必要再记录到redis，下一次出队处理成功的时候自然会记录
                    break;
            }


        }



    }

}
