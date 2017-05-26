package com.test;

import java.util.List;
import java.util.Set;

import redis.clients.jedis.Jedis;
public class RedisJava {
	public static void main(String[] args) {
		//���ӱ��ص� Redis ����
		Jedis jedis = new Jedis("192.168.1.158",6379);
		//������������
		jedis.auth("tiger");
		//�鿴�����Ƿ�����
		if(!jedis.ping().equals("PONG"))
		{
			System.out.println("����Redis����ʧ��...");
			return;
		}else
		{
			System.out.println("����Redis����ɹ�������");
		}
		/***************Redis Java String(�ַ���) ʵ��**********************/
		//���� redis �ַ�������
	    jedis.set("runoobkey", "Redis tutorial");
	    //��ȡ�洢�����ݲ����
	    System.out.println("Stored string in redis:: "+ jedis.get("runoobkey"));
	    
	    /***************Redis Java List(�б�) ʵ��**********************/
	    //�洢���ݵ��б���
	    jedis.lpush("tutorial-list", "Redis");
	    jedis.lpush("tutorial-list", "Mongodb");
	    jedis.lpush("tutorial-list", "Mysql");
	    // ��ȡ�洢�����ݲ����
	    List<String> list1 = jedis.lrange("tutorial-list", 0 ,5);
	    for(int i=0; i<list1.size(); i++) {
	    	System.out.println("Stored string in redis:: "+list1.get(i));
	    }
	    
	    /***************Redis Java Keys ʵ��**********************/
	    // ��ȡ���ݲ����
	    Set<String> set = jedis.keys("*");
	    System.out.println("List of stored keys:: "+set.toString());
	}
}