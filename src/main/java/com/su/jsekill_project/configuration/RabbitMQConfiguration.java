package com.su.jsekill_project.configuration;


import com.su.jsekill_project.bean.RabbitMQConfigBean;
import com.su.jsekill_project.service.SeckillGoodsService;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Classname RabbitMQConfiguration
 * @author: 我心
 * @Description: MQ的自动配置类
 * @Date 2023/1/18 11:45
 * @Created by Lenovo
 */
@Configuration
@EnableConfigurationProperties(RabbitMQConfigBean.class)
public class RabbitMQConfiguration {
    @Autowired
    RabbitMQConfigBean rabbitMQConfigBean;
//绑定键
//    public static final String seckill_key="seckill_mq";
    public static final String SECKILL_KEY="seckill_mq";//绑定键为固定的字符串，同时也作为秒杀队列的名称
    //创建秒杀队列
    @Bean
    public Queue seckillMq(){
        return new Queue(rabbitMQConfigBean.getSeckillQueueName());
    }
//    创建主题交换机
    @Bean
    public TopicExchange seckillTopicExchange(){
        return new TopicExchange(rabbitMQConfigBean.getSeckillTopicExchangeName());
    }
//    配置绑定描述对象，用于自动注入rabbitTemplate,这里将秒杀队列绑定到一个主题交换机中
    @Bean
    public Binding binding(){
        return BindingBuilder.bind(seckillMq()).to(seckillTopicExchange()).with(rabbitMQConfigBean.getSeckillQueueKey());
    }
    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory){
        RabbitTemplate rabbitTemplate=new RabbitTemplate();
        rabbitTemplate.setConnectionFactory(connectionFactory);
        //设置开启Mandatory，触发回调函数，无论消息推送结果如何，都强制调用回调函数
        rabbitTemplate.setMandatory(true);
        //设置回调函数
        rabbitTemplate.setConfirmCallback(new RabbitTemplate.ConfirmCallback() {
            @Override
            public void confirm(CorrelationData correlationData, boolean ack, String cause) {
                if (ack){
                    System.out.println("消息推送成功");
                }
               
            }
        });
        return rabbitTemplate;
    }
}
