package com.su.jsekill_project.controller;

import com.su.jsekill_project.Utils.DateUtil;
import com.su.jsekill_project.pojo.SeckillGoodsGroup;
import com.su.jsekill_project.redisDao.RedisDao;
import com.su.jsekill_project.service.SeckillGoodsGroupService;
import com.su.jsekill_project.service.SeckillRecordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@Controller
public class ViewController {
    @Autowired
    private RedisDao redisDao;
    @Autowired
    private SeckillRecordService recordService;
    @Autowired
    private SeckillGoodsGroupService groupService;
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @GetMapping("/setDate/")
    public String getSetDateView(){
        return "SekillDate";
    }
    @PostMapping("/setDate/")
    @ResponseBody
    public String setDate(@RequestParam("startDateStr") String startDateStr,
                          @RequestParam("endDateStr") String endDateStr,
                          @RequestParam("isReflush") String isReflush){
//        String startDateStr = map.get("startDateStr");
//        String endDateStr = map.get("endDateStr");
//        String isReflush = map.get("isReflush");
        //更新信息
        DateUtil dateUtil=new DateUtil();
        Date startDate = dateUtil.parse(startDateStr);
        Date endDate = dateUtil.parse(endDateStr);
        //是否刷新缓存
        if (isReflush!=null&&isReflush.length()>0){
            if ("1".equals(isReflush))
                redisDao.reFlushRedis();
        }
        //更新数据库组的秒杀时间
        SeckillGoodsGroup group = new SeckillGoodsGroup();
        group.setGroupId(1);
        group.setStart(startDate.getTime());
        group.setEnd(endDate.getTime());
        logger.info("startDate="+dateUtil.dateToStr(startDate));
        logger.info("endDate="+dateUtil.dateToStr(endDate));
        int i = groupService.updateSeckillGoodsGroup(group);
        //刷新秒杀记录表，防止用户在测试时主键冲突
        recordService.flushRecords();
        return "ok";
    }
}
