package com.su.jsekill_project.service;

import com.su.jsekill_project.mapper.SeckillRecordMapper;
import com.su.jsekill_project.pojo.SeckillRecord;
import com.su.jsekill_project.pojo.SeckillRecordKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Classname SeckillRecordServiceImpl
 * @author: 我心
 * @Description:
 * @Date 2023/1/17 23:15
 * @Created by Lenovo
 */
@Service
public class SeckillRecordServiceImpl implements SeckillRecordService{
    @Autowired
    SeckillRecordMapper seckillRecordMapper;
    @Override
    public int addSeckillRecord(SeckillRecord seckillRecord) {
        return 0;
    }

    @Override
    public int deleteSeckillRecord(SeckillRecordKey key) {
        return seckillRecordMapper.deleteseckillRecordById(key);
    }

    @Override
    public int updateSeckillRecord(SeckillRecord seckillRecord) {
        return seckillRecordMapper.updateSeckillRecord(seckillRecord);
    }

    @Override
    public SeckillRecord getOneSeckillRecord(SeckillRecordKey key) {
        return seckillRecordMapper.querySeckillRecord(key);
    }
}
