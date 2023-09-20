package com.su.jsekill_project.configuration;

import org.apache.curator.framework.CuratorFramework;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.zookeeper.config.CuratorFrameworkFactoryBean;
import org.springframework.integration.zookeeper.lock.ZookeeperLockRegistry;

/**
 * @Classname ZookeeperConfiguration
 * @author: 我心
 * @Description: zk分布式锁配置
 * @Date 2023/8/16 21:56
 * @Created by Lenovo
 */
@Configuration
public class ZookeeperConfiguration {
    //该地址地zk服务有问题（未解决）
    @Value("${zookeeper.host:192.168.234.110:2181}")
    private String zkUrl;
    //    分布式锁的工厂类对象
    @Bean
    public CuratorFrameworkFactoryBean curatorFrameworkFactoryBean() {
        return new CuratorFrameworkFactoryBean(zkUrl);
    }
    //  分布式锁的注册对象,所有锁的父节点为/HG-lock
    @Bean
    public ZookeeperLockRegistry zookeeperLockRegistry(CuratorFramework curatorFramework) {
        return new ZookeeperLockRegistry(curatorFramework, "/HG-lock");
    }
}
