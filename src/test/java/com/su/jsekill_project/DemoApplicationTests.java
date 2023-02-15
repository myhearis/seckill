package com.su.jsekill_project;

import com.google.gson.Gson;
import com.su.jsekill_project.Utils.DateUtil;
import com.su.jsekill_project.Utils.TestUtils;
import com.su.jsekill_project.bean.SessionPropertis;
import com.su.jsekill_project.constant.RedisKey;
import com.su.jsekill_project.constant.RedisKeyPrefix;
import com.su.jsekill_project.dto.UrlMd5;
import com.su.jsekill_project.mapper.SeckillGoodsGroupMapper;
import com.su.jsekill_project.mapper.SeckillGoodsMapper;
import com.su.jsekill_project.mapper.SeckillRecordMapper;
import com.su.jsekill_project.mapper.UserMapper;
import com.su.jsekill_project.pojo.*;
import com.su.jsekill_project.service.*;
import com.su.jsekill_project.type.SeckillGoodsListType;
import org.apache.tomcat.jni.Local;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SpringBootTest
@MapperScan("com/su/jsekill_project/mapper")
class DemoApplicationTests {
	@Autowired
	SeckillGoodsMapper seckillGoodsMapper;
	@Autowired
	SeckillRecordMapper recordMapper;
	@Autowired
	UserMapper userMapper;
	@Autowired
	SeckillGoodsService seckillGoodsService;
	@Autowired
	UserService userService;
	@Autowired
	SeckillRecordService seckillRecordService;
	@Autowired
	SeckillGoodsGroupMapper goodsGroupMapper;
	@Autowired
	StringRedisTemplate redisTemplate;
	@Autowired
	SessionPropertis sessionPropertis;
	@Autowired
	SessionService sessionService;
	@Autowired
	SeckillGoodsGroupService groupService;
//	测试日期转换
	@Test
	void contextLoads() {
//		//创建一个秒杀商品YYYY-MM-dd HH:mm:ss:SSS
//		SeckillGoods seckillGoods=new SeckillGoods(1,"手机", 100, "2023-01-10 22:30:00:000", "2023-01-10 23:30:00:000", "测试商品");
//		//创建商品包装类
//		SeckillGoodsWrappings seckillGoodsWrappings=new SeckillGoodsWrappings(seckillGoods);
//		System.out.println(seckillGoodsWrappings.getName());
	}
//测试SeckillGoodsMapper
	@Test
	void testSeckillGoodsMapper(){
		//通过id查询商品
		SeckillGoods seckillGoods = seckillGoodsMapper.querySeckillGoodsById(1);
		System.out.println(seckillGoods);
		//查询所有商品信息
		List<SeckillGoods> seckillGoodsList = seckillGoodsMapper.queryAllSeckillGoods();
		for (SeckillGoods goods : seckillGoodsList) {
			System.out.println(goods);
		}
	}
  //测试内容:插入商品
    @Test
    public void testInsertGoods(){
//		SeckillGoods seckillGoods=new SeckillGoods("一加9Pro", 3000, "2023-01-10 22:30:00:000", "2023-01-10 23:30:00:000", "测试商品");
//		int i = seckillGoodsMapper.insertSeckillGoods(seckillGoods);
//		//输出自增主键
//		System.out.println("主键："+seckillGoods.getId());
	}
  //测试内容:分页查询秒杀商品
    @Test
    public void testquerySeckillGoodsPage(){
		List<SeckillGoods> seckillGoodsList = seckillGoodsMapper.querySeckillGoodsPage(0, 3);
		for (SeckillGoods seckillGoods : seckillGoodsList) {
			System.out.println(seckillGoods);
		}
	}
  //测试内容:测试更新商品信息
    @Test
    public void testUpdateGoods(){
  		//查询id=5的对象，修改价格为5000，描述为hello，更新到数据库
		SeckillGoods seckillGoods = seckillGoodsMapper.querySeckillGoodsById(5);
		seckillGoods.setPrice(5000);
		seckillGoods.setDescribe("hello");
		//更新到数据库
		int i = seckillGoodsMapper.updateSeckillGoods(seckillGoods);
		System.out.println(i);
	}
  //测试内容:删除指定id的商品
    @Test
    public void testDeleteGoods(){
		int i = seckillGoodsMapper.deleteSeckillGoodsById(9);
		System.out.println(i);
	}
  //测试内容:测试添加秒杀记录
    @Test
    public void testAddRecord(){
		//创建一个记录对象
		SeckillRecordKey key=new SeckillRecordKey(2,1);
		SeckillRecord record=new SeckillRecord(key,"2023-01-10 22:30:00:000");
		int i = recordMapper.insertSeckillRecord(record);
		System.out.println(i);
	}
  //测试内容:查询秒杀记录
    @Test
    public void testQueryRecord(){
  		//创建一个key对象
		SeckillRecordKey key=new SeckillRecordKey(1,1);
		SeckillRecord record = recordMapper.querySeckillRecord(key);
		System.out.println(record.getKey());
		System.out.println(record.getSuccessTime());
	}
  //测试内容:更新秒杀记录
    @Test
    public void testUpdateRecord(){
		SeckillRecord seckillRecord = recordMapper.querySeckillRecord(new SeckillRecordKey(1, 1));
		seckillRecord.setSuccessTime("2023-01-11 22:30:00:000");
		int i = recordMapper.updateSeckillRecord(seckillRecord);
		System.out.println(i);
	}

  //测试内容:查询用户
    @Test
    public void testQueryUser(){
		User user = userMapper.queryUser(1);
		System.out.println(user);
	}
  //测试内容:添加用户
    @Test
    public void testAddUser(){
		User user=new User();
		user.setUserName("小白");
		user.setPassword("123456");
		int i = userMapper.insertUser(user);
		System.out.println(i);
		System.out.println("userId="+user.getUserId());
	}
  //测试内容:删除用户
    @Test
    public void testDeleteUser(){
		int i = userMapper.deleteUserById(2);
		System.out.println(i);
	}
  //测试内容:测试SeckillGoodsService接口使用
    @Test
    public void testSeckillGoodsService(){
  		//查询商品
//		SeckillGoods seckillGoodsById = seckillGoodsService.getSeckillGoodsById(1);
//		System.out.println(seckillGoodsById);
//		SeckillGoodsWrappings seckillGoodsWrappingsById = seckillGoodsService.getSeckillGoodsWrappingsById(1);
//		System.out.println(seckillGoodsWrappingsById.getStart());

	}
  //测试内容:测试UserService的使用
    @Test
    public void testUserService(){
//		boolean b = userService.userIsExist(1);
//		System.out.println(b);
	}
  //测试内容:测试SeckillRecordService的使用
    @Test
    public void testSeckillRecordService(){
  		//查询记录
		SeckillRecordKey key=new SeckillRecordKey(1,1);
		SeckillRecord oneSeckillRecord = seckillRecordService.getOneSeckillRecord(key);
		System.out.println(oneSeckillRecord);
		//测试插入同样键值的数据
		SeckillRecord seckillRecord=new SeckillRecord(key,"");
		//会返回影响行数为0
		int i = seckillRecordService.addSeckillRecord(seckillRecord);
		System.out.println(i);
	}
//	测试批量插入数据

  //测试内容:测试批量插入商品数据
    @Test
    public void testAddGoodsList(){
		TestUtils.addGoods(seckillGoodsMapper,goodsGroupMapper);
    }
  //测试内容:测试redis
    @Test
    public void testRedis(){
		redisTemplate.opsForValue().set("test","hello");
		String s = redisTemplate.opsForValue().get("test");
		System.out.println(s);
	}
  //测试内容:获取redis中存储的session
    @Test
    public void testGetSession(){
		String key= RedisKeyPrefix.SESSION_PRE+"F60FC504F2D38A0AD3F4C84652D5EEE8";
		String s = redisTemplate.opsForValue().get(key);
		System.out.println(s);
		HttpSession sessionInRedis = sessionService.getSessionInRedis(key);
		System.out.println(sessionInRedis);
	}
  //测试内容:测试获取session
    @Test
    public void testGetSessionToRedis() throws ClassNotFoundException {
//		Class<?> aClass = Class.forName(sessionPropertis.getSessionClassPath());
//		System.out.println(aClass);
//		User user = userMapper.queryUserByName("hello");
		User hello = userService.getUserByName("hello");
		System.out.println(hello);
	}
  //测试内容:测试通过组查询得到的商品列表
    @Test
    public void testGroup(){
		List<SeckillGoods> goods = seckillGoodsMapper.querySeckillGoodsListByGroupId(4);
		System.out.println(goods);
	}
  //测试内容:测试查询商品组，同时查询组对象内的商品集合
    @Test
    public void testGetGroupAndGoods(){
		SeckillGoodsGroup seckillGoodsGroup = goodsGroupMapper.querySeckillGoodsGroupByIdAndGoodList(4);
		System.out.println(seckillGoodsGroup.getGoodsList());
		//查询所有的商品组
		List<SeckillGoods> goodsList = goodsGroupMapper.queryAllGroup();
		System.out.println(goodsList);
	}
  //测试内容:测试json返回对象，从redis中读取的商品信息转化为java对象
    @Test
    public void testJsonToJava(){
  		//创建一个list
		List<SeckillGoods> list=new ArrayList<>();
		list.add(new SeckillGoods("华为mate20",3000, "华为20系列手机，麒麟处理器...",10,4));
		list.add(new SeckillGoods("华为",3000, "华为20系列手机，麒麟处理器...",10,4));
		list.add(new SeckillGoods("mate20",3000, "华为20系列手机，麒麟处理器...",10,4));
		//转化为json字符串
		Gson gson=new Gson();
		String s = gson.toJson(list);
		//转化为java对象
		Object o = gson.fromJson(s, new SeckillGoodsListType().getType());
		//转化为list
		List<SeckillGoods> goodsList= (List<SeckillGoods>) o;
		System.out.println(goodsList);
	}
  //测试内容:测试从redis中取出商品组，并获取开始和结束时间的date
    @Test
    public void testGetRedisGroup(){
//		SeckillGoodsGroup group = groupService.queryGroupAndLoadRedis(4);
//		System.out.println(group.getStart());
//		DateUtil dateUtil=new DateUtil();
//		Date parse = dateUtil.parse(group.getStart());
//		System.out.println("转化为时间戳:");
//		System.out.println(parse.getTime());
//		//将时间戳转化为date对象
//		Date date=new Date(parse.getTime());
//		//格式化为字符串
//		System.out.println(dateUtil.dateToStr(date));
	}
  //测试内容:测试时间转化
    @Test
    public void testDate(){
		DateUtil dateUtil=new DateUtil();
  		Date date=new Date();
		long timePre = date.getTime();
		//格式化成字符串
		String s = dateUtil.dateToStr(date);
		//将字符串解析为日期对象
		Date parse = dateUtil.parse(s);
		long timeAfter = parse.getTime();
		System.out.println("解析前的:"+timePre);
		System.out.println("解析后的:"+timeAfter);
	}
  //测试内容:测试重构后的group表
    @Test
    public void testNewGroup(){
  		TestUtils.addGoods(seckillGoodsMapper,goodsGroupMapper);
    }
  //测试内容:测试时间戳转换
    @Test
    public void testTimeLong(){
		//默认开始时间的日期类  (这里的year是从1900年开始计算，123即为1900+123=2023年)
		Date startDate=new Date(123,1, 31, 12, 30, 0);
		//默认结束时间的日期类
		Date endDate=new Date(123,1, 31, 13, 30, 0);
		//开始的时间戳
		System.out.println(startDate.getTime());
		//结束的时间戳
		System.out.println(endDate.getTime());
	}
  //测试内容:测试从redis中获取库存
    @Test
    public void testGetStorage(){
		int storageToRedis = seckillGoodsService.getStorageToRedis(2,1);
		System.out.println(storageToRedis);
	}

  //测试内容:测试在redis中操作set集合
    @Test
    public void testRedisSet(){
		//生成一个键
		String s = seckillGoodsService.enterMqGoodsKey(1, 1, 5);
		System.out.println(s);
		Long add = redisTemplate.opsForSet().add(RedisKey.ENTER_SECKIL_MQ_KEY, s);
		System.out.println("影响个数:"+add);
	}
  //测试内容:
    @Test
    public void testDateTime(){
		//月份要+1才是真实月份
		Date startDate=new Date(123,1, 8, 11, 30, 0);
		Date endDate=new Date(123,1, 15, 13, 30, 0);
//		System.out.println(startDate);
		System.out.println(startDate.getTime());
		System.out.println(endDate.getTime());
//		LocalDateTime.now().getLong();
//		long l=1675828668000;
//		Date date=new Date(1675828668000l);
//		System.out.println(date);
//		System.out.println(new Date(1678246200000l));
    }
  //测试内容:测试md5生成
    @Test
    public void testMd5(){
		UrlMd5 urlMd5 = seckillGoodsService.processUrl(1, 1);
		System.out.println(urlMd5.getMd5());
		System.out.println(urlMd5.isOpenKill());
	}
}
