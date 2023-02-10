package com.su.jsekill_project.mapper;

import com.su.jsekill_project.pojo.SeckillRecord;
import com.su.jsekill_project.pojo.SeckillRecordKey;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SeckillRecordMapper {
    //添加秒杀记录
    int insertSeckillRecord(@Param("seckillRecord") SeckillRecord seckillRecord);
    //传入主键对象，查询秒杀记录
    SeckillRecord querySeckillRecord(@Param("seckillRecordKey") SeckillRecordKey seckillRecordKey);
    //更新秒杀记录
    int updateSeckillRecord(@Param("seckillRecord") SeckillRecord seckillRecord );
    //传入主键对象，删除秒杀记录
    int deleteseckillRecordById(@Param("seckillRecordKey") SeckillRecordKey seckillRecordKey);
}