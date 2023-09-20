package com.su.jsekill_project.configuration;

import com.su.jsekill_project.bean.RabbitMQConfigBean;
import com.su.jsekill_project.customer.SeckillReceiver;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Classname MessageListenerConfiguration
 * @author: 我心
 * @Description: 手动确认消息的配置
 * @Date 2023/2/11 11:57
 * @Created by Lenovo
 */
@Configuration
public class MessageListenerConfiguration {
    @Autowired
    private CachingConnectionFactory connectionFactory;
    //自定义的队列监听类
    @Autowired
    private SeckillReceiver seckillReceiver;
    @Autowired
    private RabbitMQConfigBean rabbitMQConfigBean;
    //手动创建监听容器
    @Bean
    public SimpleMessageListenerContainer simpleMessageListenerContainer(){
        SimpleMessageListenerContainer container=new SimpleMessageListenerContainer(connectionFactory);
        //修改监听容器为手动确认
        container.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        //设置监听的队列
        container.setQueueNames(rabbitMQConfigBean.getSeckillQueueName());
        container.setMessageListener(seckillReceiver);
        return container;
    }
}
