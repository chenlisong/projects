package com.chenlisong.scan;

import com.chenlisong.util.JedisUtil;
import redis.clients.jedis.Jedis;

public class Scan {

    public static void main(String[] args) {
        Jedis jedis = JedisUtil.getJedis();
        for(int i=0; i<10000; i++) {
            jedis.setex("key"+i, 60 * 60, i+"");
        }


    }

}
