package com.duolanjian.java.eagle.util;

import java.util.LinkedList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

@Component
public class JedisUtil {
	
	public static final String NAMESPACE = "eagle-service";
	
	@Autowired
	private Config config;

	private static ShardedJedisPool jedisPool;
	
	private Log log = LogFactory.getLog(JedisUtil.class);
	
	public JedisUtil(){}
	
//	@PostConstruct
	public void instance(){
		if(!config.redisUse) {
			return;
		}
		
		List<JedisShardInfo> list = new LinkedList<JedisShardInfo>();
		try {
			String[] ipPorts = config.redisIpPort.split(",");
			for(String ipPort : ipPorts){
				String[] ip_port = ipPort.split(":");
				JedisShardInfo jedisShardInfo = new JedisShardInfo(ip_port[0], Integer.valueOf(ip_port[1]));
				list.add(jedisShardInfo);
			}
			
			JedisPoolConfig config = new JedisPoolConfig();
			config.setMaxTotal(1024);
			config.setMaxIdle(20);
			config.setMaxWaitMillis(100000);
			config.setTestOnBorrow(true);
			config.setTestOnReturn(true);
			        
			jedisPool = new ShardedJedisPool(config,list);
			
			ShardedJedis jedis = null;
			try{
				jedis = jedisPool.getResource();
				jedis.set("123", "123");
			}catch(Exception e) {
				log.error("jedis error.",e);
			}finally{
				if(jedis != null){
					jedis.close();
				}
			}
			
		} catch (Exception e) {
			log.error("jedis instance fail.",e);
		}
	}

	public void set(String key, String value) {
		if(!config.redisUse) {
			return;
		}
		ShardedJedis jedis = null;
		try{
			jedis = jedisPool.getResource();
			jedis.set(key, value);
		}catch(Exception e) {
			log.error("jedis error.",e);
		}finally{
			if(jedis != null){
				jedis.close();
			}
		}
	}

	public void set(String key, String value, int time) {
		if(!config.redisUse) {
			return;
		}
		ShardedJedis jedis = null;
		try{
			jedis = jedisPool.getResource();
			jedis.setex(key, time, value);
		}catch(Exception e) {
			log.error("jedis error.",e);
		}finally{
			if(jedis != null){
				jedis.close();
			}
		}
	}
	
	public String get(String key) {
		if(!config.redisUse) {
			return null;
		}
		ShardedJedis jedis = null;
		try{
			jedis = jedisPool.getResource();
			return jedis.get(key);
		}catch(Exception e) {
			log.error("jedis error.",e);
		}finally{
			if(jedis != null){
				jedis.close();
			}
		}
		return null;
	}
	
	public void del(String key) {
		if(!config.redisUse) {
			return;
		}
		ShardedJedis jedis = null;
		try{
			jedis = jedisPool.getResource();
			jedis.del(key);
		}catch(Exception e) {
			log.error("jedis error.",e);
		}finally{
			if(jedis != null){
				jedis.close();
			}
		}
	}
	
}
