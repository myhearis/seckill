package com.su.jsekill_project.Utils;

import com.su.jsekill_project.mapper.SeckillGoodsGroupMapper;
import com.su.jsekill_project.mapper.SeckillGoodsMapper;
import com.su.jsekill_project.pojo.SeckillGoods;
import com.su.jsekill_project.pojo.SeckillGoodsGroup;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Classname TestUtils
 * @author: 我心
 * @Description: 用于生成测试数据的工具类
 * @Date 2023/1/19 20:41
 * @Created by Lenovo
 */
public class TestUtils {
    //默认的秒杀开始时间
    public static final String START_TIME="2023-01-10 22:30:00:000";
    //默认
    public static final String END_TIME="2023-01-10 23:30:00:000";
    public static final int COUNT=10;
    public static final int GROUP_ID=1;//默认的秒杀商品组id
    //默认开始时间的日期类  (这里的year是从1900年开始计算，123即为1900+123=2023年)
    public static final Date startDate=new Date(123,1, 31, 12, 30, 0);
    //默认结束时间的日期类
    public static final Date endDate=new Date(123,1, 31, 13, 30, 0);
    //添加测试商品数据
    public static void addGoods(SeckillGoodsMapper goodsMapper, SeckillGoodsGroupMapper goodsGroupMapper){
        //创建商品组
//        SeckillGoodsGroup seckillGoodsGroup = new SeckillGoodsGroup(START_TIME, END_TIME);
        SeckillGoodsGroup seckillGoodsGroup=new SeckillGoodsGroup(startDate.getTime(),endDate.getTime());
        goodsGroupMapper.insertSeckillGroup(seckillGoodsGroup);
        //获取商品组的id
        int groupId=seckillGoodsGroup.getGroupId();
        //保存商品的集合
        List<SeckillGoods> goodsList=new ArrayList<>();
        goodsList.add(new SeckillGoods("华为mate20",3000, "华为20系列手机，麒麟处理器...",COUNT,groupId));
        goodsList.add(new SeckillGoods("iPhone12Pro",4200, "四千元抢最新款苹果手机！", COUNT,groupId));
        goodsList.add(new SeckillGoods("荣耀30pro",3500, "荣耀系列手机，仅需三千！", COUNT,groupId));
        goodsList.add(new SeckillGoods("一加8T",2000, "最新款一加旗舰手机，仅需两千元！", COUNT,groupId));
        goodsList.add(new SeckillGoods("小米10",1999, "搭载高通骁龙865处理器，极速性能，仅需1999元！", COUNT,groupId));
        goodsList.add(new SeckillGoods("中兴mate30",2000, "中兴最新旗舰手机，2000元限时抢购！", COUNT,groupId));
//        批量插入到数据库中
        int i = goodsMapper.insertSeckillGoodsList(goodsList);
        if (i==goodsList.size()){
            System.out.println("测试商品数据插入成功!共["+i+"]条数据");
        }

    }
}
