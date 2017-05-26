package com.gjw.test;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import redis.clients.jedis.Jedis;

public class JTest {

	private Jedis jedis;
	@Before
	public void init() {
		//连接本地的 Redis 服务
		jedis = new Jedis("192.168.1.158",6379);
		//设置连接密码
		jedis.auth("tiger");
		//查看服务是否运行
		if(!jedis.ping().equals("PONG"))
		{
			System.out.println("连接Redis服务失败...");
		}else
		{
			System.out.println("连接Redis服务成功！！！");
		}
	}
	
	/**
	 * String测试
	 */
	@Test
	public void stringTest() {
		// 添加数据
		jedis.set("name", "Usher");
		System.out.println("添加数据:" + jedis.get("name"));
		// 拼接数据
		jedis.append("name", " is very cool!");
		System.out.println("拼接数据:" + jedis.get("name"));
		// 删除数据
		jedis.del("name");
		System.out.println("删除数据:" + jedis.get("name"));
		// 设置多个数据
		jedis.mset("name", "Usher", "age", "24", "sex", "male");
		// 某个数据+
		jedis.incrBy("age", 10);
		System.out.println("姓名:" + jedis.get("name") + " 年龄:"
				+ jedis.get("age") + " 性别:" + jedis.get("sex"));
	}
	
	/**
	 * Map测试
	 */
	@Test
	public void mapTest() {
		Map<String, String> map = new HashMap<String, String>();
		// 添加数据
		map.put("name", "Usher");
		map.put("age", "24");
		map.put("sex", "male");
		// 添加到redis中
		jedis.hmset("Information", map);
		System.out.println("添加Map数据:" + jedis.hmget("Information", "name", "age", "sex"));
		// 删除数据
		jedis.hdel("Information", "name");
		System.out.println("删除Map数据:" + jedis.hmget("Information", "name"));
		// 返回key=Information的值的个数
		System.out.println("返回key=Information的值的个数:" + jedis.hlen("Information"));
		// 判断是否存在key=Information的对象
		System.out.println("判断是否存在key=Information的对象:" + jedis.exists("Information"));
		// 返回map对象中的所有key值
		System.out.println("返回map对象中的所有key值:" + jedis.hkeys("Information"));
		// 返回map对象中的所有value值
		System.out.println("返回map对象中的所有value值:" + jedis.hvals("Information"));
		// 循环迭代key
		Iterator<String> it = jedis.hkeys("Information").iterator();
		// 判断是否有元素存在
		while (it.hasNext()) {
			// 获取元素key
			String key = it.next();
			System.out.println("返回key为" + key + "的值:" + jedis.hmget("Information", key));
		}
	}
	
	/**
	 * List测试
	 */
	@Test
	public void listTest() {
		// 先清空List
		jedis.del("Information");
		// 查看List是否清空
		System.out.println("List[清空后]:" + jedis.lrange("Information", 0, -1));
		// 添加数据
		jedis.lpush("Information", "Usher");
		jedis.lpush("Information", "");
		jedis.lpush("Information", "");
		jedis.lpush("Information", "sex");
		jedis.lpush("Information", "age");
		// 查看List元素
		System.out.println("List[添加后]:" + jedis.lrange("Information", 0, -1));
		// 清空List
		jedis.del("Information");
		// 添加数据
		jedis.lpush("Information", "Elliot");
		jedis.lpush("Information", "sex");
		jedis.lpush("Information", "age");
		// 查看List元素
		System.out.println("List[清空+添加]:" + jedis.lrange("Information", 0, -1));
	}
}
