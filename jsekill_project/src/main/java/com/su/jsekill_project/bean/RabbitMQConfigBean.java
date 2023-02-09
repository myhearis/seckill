package com.su.jsekill_project.bean;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Classname RabbitMQConfigBean
 * @author: 我心
 * @Description: 关于mq的一些基本配置
 * @Date 2023/2/7 20:47
 * @Created by Lenovo
 */
@Component
@ConfigurationProperties(prefix = "rabbitmqbean")
public class RabbitMQConfigBean {
    //秒杀队列的名称
    private String seckillQueueName="seckill_queue";
    //秒杀队列的主题交换机名称SECKILL_TOPIC_EXCHANGE_NAME
    private  String  seckillTopicExchangeName="seckill_exchange";
    //秒杀队列的绑定键
    private String seckillQueueKey;

    public String getSeckillQueueName() {
        return seckillQueueName;
    }

    public void setSeckillQueueName(String seckillQueueName) {
        this.seckillQueueName = seckillQueueName;
    }

    public String getSeckillTopicExchangeName() {
        return seckillTopicExchangeName;
    }

    public void setSeckillTopicExchangeName(String seckillTopicExchangeName) {
        this.seckillTopicExchangeName = seckillTopicExchangeName;
    }

    public String getSeckillQueueKey() {
        return seckillQueueKey;
    }

    public void setSeckillQueueKey(String seckillQueueKey) {
        this.seckillQueueKey = seckillQueueKey;
    }
}
