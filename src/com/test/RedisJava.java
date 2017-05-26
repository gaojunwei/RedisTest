package com.test;

import java.util.List;
import java.util.Set;

import redis.clients.jedis.Jedis;
public class RedisJava {
	public static void main(String[] args) {
		//连接本地的 Redis 服务
		Jedis jedis = new Jedis("192.168.1.158",6379);
		//设置连接密码
		jedis.auth("tiger");
		//查看服务是否运行
		if(!jedis.ping().equals("PONG"))
		{
			System.out.println("连接Redis服务失败...");
			return;
		}else
		{
			System.out.println("连接Redis服务成功！！！");
		}
		/***************Redis Java String(字符串) 实例**********************/
		//设置 redis 字符串数据
	    jedis.set("runoobkey", "Redis tutorial");
	    //获取存储的数据并输出
	    System.out.println("Stored string in redis:: "+ jedis.get("runoobkey"));
	    
	    /***************Redis Java List(列表) 实例**********************/
	    //存储数据到列表中
	    jedis.lpush("tutorial-list", "Redis");
	    jedis.lpush("tutorial-list", "Mongodb");
	    jedis.lpush("tutorial-list", "Mysql");
	    // 获取存储的数据并输出
	    List<String> list1 = jedis.lrange("tutorial-list", 0 ,5);
	    for(int i=0; i<list1.size(); i++) {
	    	System.out.println("Stored string in redis:: "+list1.get(i));
	    }
	    
	    /***************Redis Java Keys 实例**********************/
	    // 获取数据并输出
	    Set<String> set = jedis.keys("*");
	    System.out.println("List of stored keys:: "+set.toString());
	}
}