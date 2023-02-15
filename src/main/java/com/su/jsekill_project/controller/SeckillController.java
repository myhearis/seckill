package com.su.jsekill_project.controller;

import com.google.gson.Gson;
import com.su.jsekill_project.Utils.DateUtil;
import com.su.jsekill_project.constant.RedisKey;
import com.su.jsekill_project.dto.SeckillExecutionResult;
import com.su.jsekill_project.dto.SeckillResult;
import com.su.jsekill_project.dto.UrlMd5;
import com.su.jsekill_project.pojo.SeckillGoods;
import com.su.jsekill_project.pojo.SeckillGoodsGroup;
import com.su.jsekill_project.pojo.SeckillTime;
import com.su.jsekill_project.pojo.User;
import com.su.jsekill_project.redisDao.RedisDao;
import com.su.jsekill_project.service.SeckillGoodsGroupService;
import com.su.jsekill_project.service.SeckillGoodsService;
import com.su.jsekill_project.service.UserService;
import com.su.jsekill_project.type.SeckillGoodsListType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

/**
 * @Classname SeckillController
 * @author: 我心
 * @Description:
 * @Date 2023/1/18 23:09
 * @Created by Lenovo
 */
@Controller
@RequestMapping("/seckill")
public class SeckillController {
    @Autowired
    private SeckillGoodsService seckillGoodsService;
    @Autowired
    private SeckillGoodsGroupService groupService;
    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    private DateUtil dateUtil;
    @Autowired
    private UserService userService;
    @Autowired
    private RedisDao redisDao;

//    异步请求获取所有商品
    @RequestMapping("/goodsList")
    @ResponseBody
    public SeckillResult<List<SeckillGoods>> goodsList(){
        //返回的json对象
        SeckillResult<List<SeckillGoods>> result=new SeckillResult<>();
        //获取所有商品列表
        List<SeckillGoods> seckillGoodsList = seckillGoodsService.getSeckillGoodsList();
        //添加数据
        result.setData(seckillGoodsList);
        //判断是否开启了秒杀
//        boolean isSeckill = seckillGoodsService.isSeckill(seckillGoodsList.get(0).getStart(), seckillGoodsList.get(0).getEnd());
//        result.setSeckill(isSeckill);
        return result;
    }
    //异步请求获取所有商品信息 （从redis中获取）,默认查第1组的数据
    @RequestMapping("/goodsListRedis/{groupId}")
    @ResponseBody
    public SeckillResult<List<SeckillGoods>> goodsListRedis(@PathVariable("groupId") int groupId){
        System.out.println(groupId);
        SeckillResult<List<SeckillGoods>> seckillResult=new SeckillResult<>();
        //判断redis中是否存在信息
        String s = redisTemplate.opsForValue().get(RedisKey.SECKILL_GOODS_KEY);
        //redis中有数据的情况
        if (s!=null&&s.length()!=0){
            //将json数据转化为java对象
            Gson gson=new Gson();
            Object o = gson.fromJson(s, new SeckillGoodsListType().getType());
            List<SeckillGoods> goodsList= (List<SeckillGoods>) o;
            //直接返回数据
            seckillResult.setData(goodsList);
            return seckillResult;
        }
        //redis中没有数据的情况
        else {
            //从数据库中查询
            List<SeckillGoods> goodsList = seckillGoodsService.getSeckillGoodsByGroupId(groupId);
            //将数据加载到redis中
            seckillGoodsService.loadSeckillGoodsToRedis(goodsList);
            //设置数据到数据封装对象中
            seckillResult.setData(goodsList);
            return seckillResult;
        }
    }




    //异步返回商品组对象，但不包括商品集合，(用于给前台展示秒杀开始的时间段)
    @RequestMapping("/time/{groupId}")
    @ResponseBody
    public SeckillResult<SeckillTime> time(@PathVariable("groupId") int groupId){
        System.out.println("查询时间，组id="+groupId);
        //返回信息的对象
        SeckillResult<SeckillTime> result=new SeckillResult<>();
       //加载对应的商品组，默认组id=4
        SeckillGoodsGroup group = groupService.queryGroupAndLoadRedis(groupId);
        //封装商品组的时间段
        long start=group.getStart();
        long end=group.getEnd();
        SeckillTime seckillTime=new SeckillTime(start,end);
        //设置数据
        result.setData(seckillTime);
        return result;
    }
    //处理秒杀请求
    @RequestMapping("/pay/{goodsId}/{groupId}")
    @ResponseBody
    public SeckillResult seckillGoods(@PathVariable("goodsId") int goodsId, @PathVariable("groupId") int groupId, HttpServletRequest request){
        //要返回的信息封装对象
        SeckillResult<SeckillExecutionResult> result=new SeckillResult<>();
        //判断用户是否已经登录
        HttpSession session = request.getSession();
        Object user = session.getAttribute("user");
        if (user==null){
            SeckillResult seckillResult = new SeckillResult();
            SeckillExecutionResult executionResult=new SeckillExecutionResult();
            executionResult.setStateInfo("用户未登陆，请登陆");
            seckillResult.setData(executionResult);
            return seckillResult;
        }
        //获取userID
        User thisUser= (User) user;
        int userId = thisUser.getUserId();
        //处理秒杀逻辑
        SeckillExecutionResult executionResult = seckillGoodsService.seckillProcess(goodsId, groupId, userId);
        result.setData(executionResult);
        return result;
    }
    @RequestMapping("/state/{goodsId}/{groupId}")
    @ResponseBody
    public SeckillResult getSeckillState(@PathVariable("goodsId") int goodsId,@PathVariable("groupId") int groupId, HttpSession session){
        //获取登陆用户的session
        Object o = session.getAttribute("user");
        User user=null;
        if (o!=null&&o instanceof User)
            user=(User) o;
        //调用判断方法
        SeckillResult result = seckillGoodsService.getSeckillState(goodsId, groupId, user.getUserId());
        return  result;
    }
    @RequestMapping("/md5/{goodsId}/{groupId}")
    @ResponseBody
    public SeckillResult getMd5(@PathVariable("goodsId") int goodsId,@PathVariable("groupId") int groupId) {
        SeckillResult<UrlMd5> result=new SeckillResult<>();
        //获取md5的内容
        UrlMd5 urlMd5 = seckillGoodsService.processUrl(goodsId, groupId);
        //封装到结果中
        result.setData(urlMd5);
        return result;
    }
    //处理秒杀请求方法2
    @RequestMapping("/pay/{goodsId}/{groupId}/{md5}")
    @ResponseBody
    public SeckillResult seckillGoods2(@PathVariable("goodsId") int goodsId, @PathVariable("groupId") int groupId, HttpServletRequest request,@PathVariable("md5") String md5){
        //要返回的信息封装对象
        SeckillResult<SeckillExecutionResult> result=new SeckillResult<>();
        //判断用户是否已经登录
        HttpSession session = request.getSession();
        Object user = session.getAttribute("user");
        if (user==null){
            SeckillResult seckillResult = new SeckillResult();
            SeckillExecutionResult executionResult=new SeckillExecutionResult();
            executionResult.setStateInfo("用户未登陆，请登陆");
            seckillResult.setData(executionResult);
            return seckillResult;
        }
        //获取userID
        User thisUser= (User) user;
        int userId = thisUser.getUserId();
        //处理秒杀逻辑
        SeckillExecutionResult executionResult = seckillGoodsService.seckillProcess(goodsId, groupId, userId,md5);
        result.setData(executionResult);
        return result;
    }
}
