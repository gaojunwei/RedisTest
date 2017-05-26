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
		//���ӱ��ص� Redis ����
		jedis = new Jedis("192.168.1.158",6379);
		//������������
		jedis.auth("tiger");
		//�鿴�����Ƿ�����
		if(!jedis.ping().equals("PONG"))
		{
			System.out.println("����Redis����ʧ��...");
		}else
		{
			System.out.println("����Redis����ɹ�������");
		}
	}
	
	/**
	 * String����
	 */
	@Test
	public void stringTest() {
		// �������
		jedis.set("name", "Usher");
		System.out.println("�������:" + jedis.get("name"));
		// ƴ������
		jedis.append("name", " is very cool!");
		System.out.println("ƴ������:" + jedis.get("name"));
		// ɾ������
		jedis.del("name");
		System.out.println("ɾ������:" + jedis.get("name"));
		// ���ö������
		jedis.mset("name", "Usher", "age", "24", "sex", "male");
		// ĳ������+
		jedis.incrBy("age", 10);
		System.out.println("����:" + jedis.get("name") + " ����:"
				+ jedis.get("age") + " �Ա�:" + jedis.get("sex"));
	}
	
	/**
	 * Map����
	 */
	@Test
	public void mapTest() {
		Map<String, String> map = new HashMap<String, String>();
		// �������
		map.put("name", "Usher");
		map.put("age", "24");
		map.put("sex", "male");
		// ��ӵ�redis��
		jedis.hmset("Information", map);
		System.out.println("���Map����:" + jedis.hmget("Information", "name", "age", "sex"));
		// ɾ������
		jedis.hdel("Information", "name");
		System.out.println("ɾ��Map����:" + jedis.hmget("Information", "name"));
		// ����key=Information��ֵ�ĸ���
		System.out.println("����key=Information��ֵ�ĸ���:" + jedis.hlen("Information"));
		// �ж��Ƿ����key=Information�Ķ���
		System.out.println("�ж��Ƿ����key=Information�Ķ���:" + jedis.exists("Information"));
		// ����map�����е�����keyֵ
		System.out.println("����map�����е�����keyֵ:" + jedis.hkeys("Information"));
		// ����map�����е�����valueֵ
		System.out.println("����map�����е�����valueֵ:" + jedis.hvals("Information"));
		// ѭ������key
		Iterator<String> it = jedis.hkeys("Information").iterator();
		// �ж��Ƿ���Ԫ�ش���
		while (it.hasNext()) {
			// ��ȡԪ��key
			String key = it.next();
			System.out.println("����keyΪ" + key + "��ֵ:" + jedis.hmget("Information", key));
		}
	}
	
	/**
	 * List����
	 */
	@Test
	public void listTest() {
		// �����List
		jedis.del("Information");
		// �鿴List�Ƿ����
		System.out.println("List[��պ�]:" + jedis.lrange("Information", 0, -1));
		// �������
		jedis.lpush("Information", "Usher");
		jedis.lpush("Information", "");
		jedis.lpush("Information", "");
		jedis.lpush("Information", "sex");
		jedis.lpush("Information", "age");
		// �鿴ListԪ��
		System.out.println("List[��Ӻ�]:" + jedis.lrange("Information", 0, -1));
		// ���List
		jedis.del("Information");
		// �������
		jedis.lpush("Information", "Elliot");
		jedis.lpush("Information", "sex");
		jedis.lpush("Information", "age");
		// �鿴ListԪ��
		System.out.println("List[���+���]:" + jedis.lrange("Information", 0, -1));
	}
}
