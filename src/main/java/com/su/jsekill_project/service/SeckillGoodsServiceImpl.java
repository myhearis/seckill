package com.su.jsekill_project.service;

import com.google.gson.Gson;
import com.su.jsekill_project.Utils.DateUtil;
import com.su.jsekill_project.bean.RabbitMQConfigBean;
import com.su.jsekill_project.constant.RedisKey;
import com.su.jsekill_project.constant.RedisKeyPrefix;
import com.su.jsekill_project.dto.SeckillExecutionResult;
import com.su.jsekill_project.dto.SeckillMsgBody;
import com.su.jsekill_project.dto.SeckillResult;
import com.su.jsekill_project.dto.UrlMd5;
import com.su.jsekill_project.enums.SeckillStateEnum;
import com.su.jsekill_project.mapper.SeckillGoodsMapper;
import com.su.jsekill_project.pojo.SeckillGoods;
import com.su.jsekill_project.pojo.SeckillGoodsGroup;
import com.su.jsekill_project.pojo.SeckillGoodsWrappings;
import com.su.jsekill_project.redisDao.RedisDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitOperations;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;

/**
 * @Classname SeckillGoodsServiceImpl
 * @author: 我心
 * @Description:
 * @Date 2023/1/17 22:26
 * @Created by Lenovo
 */
@Service
public class SeckillGoodsServiceImpl implements SeckillGoodsService{
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private RabbitMQConfigBean rabbitMQConfigBean;
    @Autowired
    private DateUtil dateUtil;
    @Autowired
    private SeckillGoodsMapper seckillGoodsMapper;
    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    private RedisDao redisDao;
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private Gson gson;
    private static final String SLOT="fdas%*(_$'#!";//用于混淆md5的盐值字符串
    @Override
    public int addSeckillGoods(SeckillGoods seckillGoods) {
        return seckillGoodsMapper.insertSeckillGoods(seckillGoods);
    }

    @Override
    public int deleteSeckillGoods(int seckillGoodsId) {
        return seckillGoodsMapper.deleteSeckillGoodsById(seckillGoodsId);
    }

    @Override
    public SeckillGoods getSeckillGoodsById(int seckillGoodsId) {
        return seckillGoodsMapper.querySeckillGoodsById(seckillGoodsId);
    }
//返回商品包装类，便于计算逻辑
    @Override
    public SeckillGoodsWrappings getSeckillGoodsWrappingsById(int seckillGoodsId) {
//        //获取到原生商品类
//        SeckillGoods seckillGoods = seckillGoodsMapper.querySeckillGoodsById(seckillGoodsId);
//        //包装类
//        SeckillGoodsWrappings seckillGoodsWrappings =new SeckillGoodsWrappings(seckillGoods);
        return null;
    }

    @Override
    public int updateSeckillGoods(SeckillGoods seckillGoods) {
        return seckillGoodsMapper.updateSeckillGoods(seckillGoods);
    }

    @Override
    public List<SeckillGoods> getSeckillGoodsList() {
        List<SeckillGoods> seckillGoodsList = seckillGoodsMapper.queryAllSeckillGoods();
        return seckillGoodsList;
    }

    @Override
    public boolean isSeckill(String startTime, String endTime) {
        boolean result=false;
        //将传入的开始时间和结束时间转化为毫秒数
        long start = dateUtil.parse(startTime).getTime();
        long end = dateUtil.parse(endTime).getTime();
        //获取当前时间
        Date date=new Date();
        long now = date.getTime();
        if (now>=start&&now<=end)
            result=true;
        return result;
    }
    //传入一个商品组的列表，元素是同一个商品组的列表，并存入redis中
    @Override
    public void loadSeckillGoodsToRedis(List<SeckillGoods> goodsList) {

        //将信息进行json处理
        Gson gson=new Gson();
        String s = gson.toJson(goodsList);
        //存入redis中
        redisTemplate.opsForValue().set(RedisKey.SECKILL_GOODS_KEY,s);
        //将每个商品的库存单独存储
        for (SeckillGoods seckillGoods : goodsList) {
            //获取库存key
            String stockKey = stockKey(seckillGoods);
            //存入redis中
            redisTemplate.opsForValue().set(stockKey, String.valueOf(seckillGoods.getStorage()));
        }
    }

    @Override
    public void loadSeckillGoodsGroupsToRedis(List<List<SeckillGoods>> goodsGroups) {

    }
    //优先从redis中加载商品，否则从数据库中加载，并缓存到redis中
    @Override
    public SeckillGoods getSeckillGoodsToRedisAndDataBase(int goodsId,int groupId) {
        //生成商品的redis key

        return null;
    }
    //从redis中通过key获取一个商品
    @Override
    public SeckillGoods getSeckillByRedis(String key) {

        return null;
    }

    @Override
    public int decrStorage(int goodsId, int groupId) {
        return seckillGoodsMapper.decrStorage(goodsId,groupId);
    }

    //生成商品的库存key,前缀+商品组id+商品id
    private String stockKey(SeckillGoods seckillGoods){
      return stockKey(seckillGoods.getId(),seckillGoods.getGroupId());
    }
    private String stockKey(int goodsId,int groupId){
        StringBuffer stringBuffer=new StringBuffer();
        stringBuffer.append(RedisKeyPrefix.GOODS_STOCK_PRE);//key前缀
        stringBuffer.append("-");
        stringBuffer.append(groupId);//组id
        stringBuffer.append("-");
        stringBuffer.append(goodsId);//商品id
        return stringBuffer.toString();
    }

    @Override
    public List<SeckillGoods> getSeckillGoodsByGroupId(int groupId) {
        return seckillGoodsMapper.querySeckillGoodsListByGroupId(groupId);
    }
    //从redis中获取商品列表(未完成，暂时没用到，具体逻辑在controller中写了)
    @Override
    public List<SeckillGoods> getSeckillGoodsByRedis() {
        //判断redis中是否存在商品列表
        String s = redisTemplate.opsForValue().get(RedisKey.SECKILL_GOODS_KEY);
        //redis数据不存在的情况
        if (s==null||s.length()==0){
            //从数据库中请求数据

        }
        //redis数据存在的情况
        else {

        }
        return null;
    }

    @Override
    public int getStorageToRedis(int goodsId,int groupId) {
        String s = redisTemplate.opsForValue().get(stockKey(goodsId,groupId));
        return Integer.parseInt(s);
    }

    @Override
    public SeckillExecutionResult seckillProcess(int goodsId, int groupId, int userId) {
        //创建一个返回值
        SeckillExecutionResult executionResult=new SeckillExecutionResult();
        //创建返回信息对象
        SeckillExecutionResult result=new SeckillExecutionResult();
        //校验请求是否有效（若无效，则返回）
        boolean verify = verifySeckillRequest(goodsId, groupId, userId, executionResult);
        //如果校验不通过，则直接返回秒杀信息
        if (!verify){
            return executionResult;
        }
        //这里继续校验通过的逻辑
        //封装消息发送给MQ
        SeckillMsgBody seckillMsgBody = createSeckillMsgBody(goodsId, groupId, userId);
        //将消息转换为json字符串并发送
        String msgbodyStr = gson.toJson(seckillMsgBody);
        //同步发送消息并等待一定时间mq的ack报文，如果等到了，就记录到redis中，没有则直接返回超时状态
        Boolean isEnterMQ = rabbitTemplate.invoke(new RabbitOperations.OperationsCallback<Boolean>() {
            @Override
            public Boolean doInRabbit(RabbitOperations operations) {
                //发送给MQ...

                rabbitTemplate.convertAndSend(rabbitMQConfigBean.getSeckillTopicExchangeName(), rabbitMQConfigBean.getSeckillQueueKey(), msgbodyStr);
                return operations.waitForConfirms(100);
            }
        });

        //如果等到了
        if (isEnterMQ){
            logger.info("成功发送消息给mq"+seckillMsgBody);
            //记录到redis当中
            redisDao.enterMqRequest(goodsId,groupId,userId);
            //设置状态为排队中
            SeckillStateEnum enqueuePreSeckill = SeckillStateEnum.ENQUEUE_PRE_SECKILL;
            executionResult.setState(enqueuePreSeckill.getState());
            executionResult.setStateInfo(enqueuePreSeckill.getStateInfo());
        }
        //如果没等到,返回让用户继续等待或者重试
        else {
            logger.info("mq的ack超时");
            SeckillStateEnum mq_ack_timeOut = SeckillStateEnum.MQ_Ack_TimeOut;
            executionResult.setState(mq_ack_timeOut.getState());
            executionResult.setStateInfo(mq_ack_timeOut.getStateInfo());
        }
        return executionResult;
    }


    //校验前端请求是否有效,这里没有判断md5
    //无效情况（重复秒杀。秒杀时间未到，商品库存小于0）
    //返回值为为秒杀的状态
    @Override
    public boolean verifySeckillRequest(int goodsId, int groupId, int userId,SeckillExecutionResult seckillExecutionResult) {
        boolean re=true;//请求是否校验通过
        SeckillStateEnum stateEnum=SeckillStateEnum.Verify_Request_Sucess;//校验后的状态枚举类,默认值为校验通过
        seckillExecutionResult.setGroupId(goodsId);
        seckillExecutionResult.setGroupId(groupId);
        //判断用户之前是否重复秒杀
        //查询redis中是否存在该用户的记录
        //获取当前秒杀请求生成的排队记录键（不一定存在，如果重复秒杀就会存在）
        String enterMqGoodsKey = enterMqGoodsKey(goodsId, groupId, userId);

        //操作排队记录的set集合,判断当前用户是否存在对该商品的秒杀记录
        Boolean member = redisTemplate.opsForSet().getOperations().boundSetOps(RedisKey.ENTER_SECKIL_MQ_KEY).isMember(enterMqGoodsKey);
        //重复秒杀
        if (member){
            //设置状态为重复秒杀
           stateEnum = SeckillStateEnum.REPEAT_KILL;
           re=false;
        }
        //判断秒杀时间是否到
        if (!isSeckillTime(groupId)){
            //不再秒杀时间段内,校验失败
            stateEnum=SeckillStateEnum.Verify_Failed;
            re=false;
        }
        //判断商品库存是否充足
        int storageToRedis = getStorageToRedis(goodsId, groupId); //从redis中获取某个商品的库存
        if (storageToRedis<=0){
            stateEnum=SeckillStateEnum.SOLD_OUT;
            re=false;
        }
        //设置状态信息
        seckillExecutionResult.setStateInfo(stateEnum.getStateInfo());
        seckillExecutionResult.setState(stateEnum.getState());
        return re;
    }
    //生成对应商品的进入秒杀队列保存在set集合中的key
    //(用户id@商品组id@商品id)
    @Override
    public String enterMqGoodsKey(int goodsId, int groupId,int userId) {
        StringBuffer stringBuffer=new StringBuffer();
        stringBuffer.append(userId);
        stringBuffer.append('@');
        stringBuffer.append(groupId);
        stringBuffer.append('@');
        stringBuffer.append(goodsId);
        return stringBuffer.toString();
    }

    @Override
    public SeckillMsgBody createSeckillMsgBody(int goodsId, int groupId, int userId) {
        //创建秒杀消息请求对象
        SeckillMsgBody msgBody=new SeckillMsgBody();
        msgBody.setGoodsId(goodsId);
        msgBody.setGroupId(groupId);
        msgBody.setUserId(userId);
        return msgBody;
    }
    //异步回调方法
    @Override
    public void confirmCallback(CorrelationData correlationData, boolean ack, String cause) {
        //如果收到了mq的确认，证明推送成功
        if (ack){
            //将当前用户的请求记录到redis中

        }
    }
    //返回值：1：秒杀成功 2：排队中
    @Override
    public SeckillResult getSeckillState(int goodsId, int groupId, int userId) {
        SeckillResult<Integer>  result=new SeckillResult<>();
        //判断是否进入队列中
        boolean enterMQ = redisDao.isEnterMQ(goodsId, groupId, userId);
        if (enterMQ){
            result.setData(1);
        }
        else {
            result.setData(2);
        }
        return result;
    }

    @Override
    public String getMD5(int goodsId, int groupId) {
        String md5="  ";

        try {
            md5= DigestUtils.md5DigestAsHex(toBytes(goodsId,groupId));
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("md5计算出现错误!");
        }

        return md5;
    }
    //处理md5的url请求
    @Override
    public UrlMd5 processUrl(int goodsId, int groupId) {
        UrlMd5 urlMd5=new UrlMd5();
        //生成md5
        String md5 = getMD5(goodsId, groupId);
        //获取商品的开始和结束时间
        //从redis中获取商品组
        SeckillGoodsGroup goodsGroup = redisDao.getGoodsGroup(groupId);
        Date date = new Date();
        //判断是否开启了秒杀
        boolean seckillTime = isSeckillTime(goodsGroup.getStart(), goodsGroup.getEnd(),date.getTime());
        //设置信息
        urlMd5.setStartTime(goodsGroup.getStart());
        urlMd5.setEndTime(goodsGroup.getEnd());
        urlMd5.setMd5(md5);
        urlMd5.setOpenKill(seckillTime);
        urlMd5.setNowTime(date.getTime());
        return urlMd5;
    }

    @Override
    public boolean verifyMd5(int goodsId, int groupId,String md5) {
        //重新计算一次md5
        String nowMd5=getMD5(goodsId, groupId);
        //判断是否相同
        if (nowMd5.equals(md5))
            return true;
        return false;
    }

    @Override
    public boolean verifySeckillRequest(int goodsId, int groupId, int userId, SeckillExecutionResult seckillExecutionResult, String md5) {
        //先判断md5
        boolean verifyMd5 = verifyMd5(goodsId, groupId, md5);
        seckillExecutionResult.setGroupId(goodsId);
        seckillExecutionResult.setGroupId(groupId);
        if (!verifyMd5){
            SeckillStateEnum dataRewrite = SeckillStateEnum.DATA_REWRITE;
            seckillExecutionResult.setState(dataRewrite.getState());
            seckillExecutionResult.setStateInfo(dataRewrite.getStateInfo());
        }
        //再判断其他
        boolean b = verifySeckillRequest(goodsId, groupId, userId, seckillExecutionResult);
        return b;
    }

    @Override
    public SeckillExecutionResult seckillProcess(int goodsId, int groupId, int userId, String md5) {
        //创建一个返回值
        SeckillExecutionResult executionResult=new SeckillExecutionResult();
        //创建返回信息对象
        SeckillExecutionResult result=new SeckillExecutionResult();
        //校验请求是否有效（若无效，则返回）
        boolean verify = verifySeckillRequest(goodsId, groupId, userId, executionResult,md5);
        //如果校验不通过，则直接返回秒杀信息
        if (!verify){
            return executionResult;
        }
        //这里继续校验通过的逻辑
        //封装消息发送给MQ
        SeckillMsgBody seckillMsgBody = createSeckillMsgBody(goodsId, groupId, userId);
        //将消息转换为json字符串并发送
        String msgbodyStr = gson.toJson(seckillMsgBody);
        //同步发送消息并等待一定时间mq的ack报文，如果等到了，就记录到redis中，没有则直接返回超时状态
        Boolean isEnterMQ = rabbitTemplate.invoke(new RabbitOperations.OperationsCallback<Boolean>() {
            @Override
            public Boolean doInRabbit(RabbitOperations operations) {
                //发送给MQ...

                rabbitTemplate.convertAndSend(rabbitMQConfigBean.getSeckillTopicExchangeName(), rabbitMQConfigBean.getSeckillQueueKey(), msgbodyStr);
                return operations.waitForConfirms(100);
            }
        });

        //如果等到了
        if (isEnterMQ){
            logger.info("成功发送消息给mq"+seckillMsgBody);
            //记录到redis当中
            redisDao.enterMqRequest(goodsId,groupId,userId);
            //设置状态为排队中
            SeckillStateEnum enqueuePreSeckill = SeckillStateEnum.ENQUEUE_PRE_SECKILL;
            executionResult.setState(enqueuePreSeckill.getState());
            executionResult.setStateInfo(enqueuePreSeckill.getStateInfo());
        }
        //如果没等到,返回让用户继续等待或者重试
        else {
            logger.info("mq的ack超时");
            SeckillStateEnum mq_ack_timeOut = SeckillStateEnum.MQ_Ack_TimeOut;
            executionResult.setState(mq_ack_timeOut.getState());
            executionResult.setStateInfo(mq_ack_timeOut.getStateInfo());
        }
        return executionResult;
    }

    private byte[] toBytes(int goodsId,int groupId){
        StringBuffer stringBuffer=new StringBuffer();
        stringBuffer.append(goodsId);
        stringBuffer.append(groupId);
        return stringBuffer.toString().getBytes(StandardCharsets.UTF_8);
    }

    //判断当前服务器时间是否在秒杀时间段内
    public boolean isSeckillTime(int groupId){
        Date date=new Date();
        //从redis中查询商品组
        SeckillGoodsGroup goodsGroup = redisDao.getGoodsGroup(groupId);
        long start = goodsGroup.getStart();
        long end = goodsGroup.getEnd();
        long now = date.getTime();
        //当前时间戳>开始时间戳&&当前时间戳<结束时间戳
        if (now>start&&now<end)
            return true;
        return false;
    }
    private boolean isSeckillTime(long startTime,long endTime,long nowTime){
        if (nowTime>startTime&&nowTime<endTime){
            return true;
        }
        return false;
    }

}
