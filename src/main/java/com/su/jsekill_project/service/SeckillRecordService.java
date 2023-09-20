package com.su.jsekill_project.service;

import com.su.jsekill_project.pojo.SeckillRecord;
import com.su.jsekill_project.pojo.SeckillRecordKey;

/**
 * @Classname SeckillRecordService
 * @author: 我心
 * @Description:
 * @Date 2023/1/17 22:03
 * @Created by Lenovo
 */
public interface SeckillRecordService {
//    添加记录
    int addSeckillRecord(SeckillRecord seckillRecord);
//    删除记录
    int deleteSeckillRecord(SeckillRecordKey key);
    //更新记录
    int updateSeckillRecord(SeckillRecord seckillRecord);
//    查找记录
    SeckillRecord getOneSeckillRecord(SeckillRecordKey key);
    //刷新record表
    void flushRecords();
}
