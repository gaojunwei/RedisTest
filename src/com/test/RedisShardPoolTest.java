package com.test;


import java.util.ArrayList;
import java.util.List;

import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;
import redis.clients.util.Hashing;
import redis.clients.util.Sharded;

public class RedisShardPoolTest {
	static ShardedJedisPool pool;
    static{
        JedisPoolConfig config =new JedisPoolConfig();//Jedis������
        config.setMaxIdle(500);//����Ķ������
        config.setMaxIdle(1000 * 60);//����������ʱ��
        config.setMaxWaitMillis(1000 * 10);//��ȡ����ʱ���ȴ�ʱ��
        config.setTestOnBorrow(true);
        String hostA = "192.168.1.126";
        int portA = 6379;
        String hostB = "192.168.1.158";
        int portB = 6379;
        List<JedisShardInfo> jdsInfoList =new ArrayList<JedisShardInfo>(2);
        JedisShardInfo infoA = new JedisShardInfo(hostA, portA);
        infoA.setPassword("redis.360buy");
        JedisShardInfo infoB = new JedisShardInfo(hostB, portB);
        infoB.setPassword("redis.360buy");
        jdsInfoList.add(infoA);
        jdsInfoList.add(infoB);
       
        pool = new ShardedJedisPool(config, jdsInfoList, Hashing.MURMUR_HASH,
        		Sharded.DEFAULT_KEY_TAG_PATTERN);
    }
   
    /**
     * @param args
     */
    public static void main(String[] args) {
        for(int i=0; i<100; i++){
           String key =generateKey();
           //key += "{aaa}";
           ShardedJedis jds =null;
           try {
               jds =pool.getResource();
               System.out.println(key+":"+jds.getShard(key).getClient().getHost());
               System.out.println(jds.set(key,"1111111111111111111111111111111"));
           }catch (Exception e) {
               e.printStackTrace();
           }
           finally{
               pool.close();
           }
        }
    }
 
    private static int index = 1;
    
    public static String generateKey(){
        return String.valueOf(Thread.currentThread().getId())+"_"+(index++);
    }
}
