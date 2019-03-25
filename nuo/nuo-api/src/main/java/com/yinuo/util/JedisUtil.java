package com.yinuo.util;

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

import com.alibaba.fastjson.JSON;

@Component
public class JedisUtil {
	
	@Autowired
	private Config config;

	private ShardedJedisPool jedisPool;
	
	private Log log = LogFactory.getLog(JedisUtil.class);
	
	public JedisUtil(){}
	
	@PostConstruct
	public void instance(){
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
			config.setTestOnBorrow(true);
			config.setTestOnReturn(true);
			        
			jedisPool = new ShardedJedisPool(config,list);
		} catch (Exception e) {
			log.error("jedis instance fail.",e);
		}
	}

	public void set(String key, String value) {
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
	
	@SuppressWarnings("unchecked")
	public <T> T get(String key,T t){
		String value = this.get(key);
		if(value != null && !"".equals(value)){
			try {
				t = (T) JSON.parseObject(value, t.getClass());
				if(t != null){
					return t;
				}
			} catch (Exception e) {
				return null;
			}
		}
		return null;
	}
	
}
