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
	
	public <T> T get(String key,Class clazz){
		String value = this.get(key);
		if(value != null && !"".equals(value)){
			try {
				return (T) JSON.parseObject(value, clazz);
			} catch (Exception e) {
				return null;
			}
		}
		return null;
	}

	public <T> List<T> getArray(String key, Class clazz) {
		String value = this.get(key);
		if(value != null && !"".equals(value)){
			try {
				return JSON.parseArray(value, clazz);
			} catch (Exception e) {
				return null;
			}
		}
		return null;
	}

	/**
	 * 插到list尾部
	 * @param key
	 * @param object
	 */
	public void rpush(String key, Object object) {
		if(object == null) {
			return;
		}

		ShardedJedis jedis = null;
		try{
			jedis = jedisPool.getResource();
			jedis.rpush(key, JSON.toJSONString(object));
		}catch(Exception e) {
			log.error("jedis error.",e);
		}finally{
			if(jedis != null){
				jedis.close();
			}
		}
	}

	/**
	 * 移除头部数据
	 * @param key
	 * @param clazz
	 * @param <T>
	 * @return
	 */
	public <T> T lpop(String key, Class clazz) {
		ShardedJedis jedis = null;
		try{
			jedis = jedisPool.getResource();
			String result = jedis.lpop(key);
			if(result != null && result.length() > 0 && !"(nil)".equals(result)) {
				return (T) JSON.parseObject(result, clazz);
			}
		}catch(Exception e) {
			log.error("jedis error.",e);
		}finally{
			if(jedis != null){
				jedis.close();
			}
		}
		return null;
	}

	/**
	 * 取list第一个数据
	 * @param key
	 * @param clazz
	 * @param <T>
	 * @return
	 */
	public <T> T ltop(String key, Class clazz) {
		ShardedJedis jedis = null;
		try{
			jedis = jedisPool.getResource();
			String result = jedis.lindex(key, 0);
			if(result != null && result.length() > 0 && !"(nil)".equals(result)) {
				return (T) JSON.parseObject(result, clazz);
			}
		}catch(Exception e) {
			log.error("jedis error.",e);
		}finally{
			if(jedis != null){
				jedis.close();
			}
		}
		return null;
	}

}
