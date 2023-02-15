package com.su.jsekill_project.controller;

import com.su.jsekill_project.pojo.SeckillGoods;
import com.su.jsekill_project.service.MyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Classname IndexController
 * @author: 我心
 * @Description:
 * @Date 2023/1/12 12:14
 * @Created by Lenovo
 */
@Controller
public class IndexController {
    @Autowired
    private MyService myService;
    @RequestMapping("/")
    public String index(){
        return "/index";
    }
    @GetMapping("/jsekill/index")
    public String jsekillIndex(){
        return "/index";
    }
    @RequestMapping("/login")
    public String login(){
        return "login";
    }
    @RequestMapping("/add")
    @ResponseBody
    public int testAdd(){
        //创建一个秒杀商品YYYY-MM-dd HH:mm:ss:SSS
//        SeckillGoods seckillGoods=new SeckillGoods(1,"手机", 100, "2023-01-10 22:30:00:000", "2023-01-10 23:30:00:000", "测试商品");
//        int i = myService.addSeckillGoods(seckillGoods);
//        System.out.println(i);
//        return i;
        return 0;
    }
    @RequestMapping("jsekill/index2")
    public String index2(){
        return "index2";
    }
}
