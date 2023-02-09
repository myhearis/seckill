package com.su.jsekill_project.Utils;

import com.su.jsekill_project.bean.SeckillRemainTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Classname DateUtil
 * @author: 我心
 * @Description: 日期工具类
 * @Date 2023/1/12 23:33
 * @Created by Lenovo
 */
@Component
public class DateUtil {
    private static final Logger logger = LoggerFactory.getLogger(DateUtil.class);
    //精确到毫秒数，便于解析时得到的毫秒数与原来的毫秒数相同
    public static final String DEFAULT_FORMAT="YYYY-MM-DD HH:mm:ss:SSS";
    private String format=DEFAULT_FORMAT;
    public static final SimpleDateFormat simpleDateFormat=new SimpleDateFormat(DEFAULT_FORMAT);

    //传入日期，格式化成字符串
    public String dateToStr(Date date){
        String format = simpleDateFormat.format(date);
        return format;
    }
    //传入字符串，解析为日期对象
    public Date parse(String dateStr){
        Date parse=null;
        try {

            parse = simpleDateFormat.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
            logger.error("日期解析失败！检查格式是否正确",e);
        }
        return parse;

    }
    //获取一个毫秒数，转化为剩余时间类的对象
    public SeckillRemainTime getRemainTime(long time){
        //先将毫秒数转化为秒
        long baseSecond=time/1000;
        //计算小时
        long hour= baseSecond/3600;
        baseSecond=baseSecond%3600;
        //计算分钟
        long minutes=baseSecond/60;
        baseSecond=baseSecond%60;//剩下的秒数
       //封装到剩余时间对象中
        SeckillRemainTime remainTime=new SeckillRemainTime();
        remainTime.setHour(hour);
        remainTime.setMinute(minutes);
        remainTime.setSecond(baseSecond);
        return remainTime;
    }

    public static void main(String[] args) {
        DateUtil dateUtil=new DateUtil();
//        Date date = new Date();
//        System.out.println(date.getTime());
//        String s = dateUtil.dateToStr(date);
//        System.out.println(s);
//        Date parse = dateUtil.parse(s);
//        System.out.println(parse.getTime());

        //测试封装剩余时间
        SeckillRemainTime remainTime = dateUtil.getRemainTime(123500026);
        System.out.println("剩余小时:"+remainTime.getHour());
        System.out.println("剩余分钟:"+remainTime.getMinute());
        System.out.println("剩余秒数:"+remainTime.getSecond());
    }
}
