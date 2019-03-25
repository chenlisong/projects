package com.chenlisong.currentlock;

import com.chenlisong.util.JedisUtil;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class JedisDaemonThread implements Runnable{

    private List<String> keys = new ArrayList<String>();

    Timer timer = new Timer();

    public void addKey(String key) {
        this.keys.add(key);
    }

    @Override
    public void run() {
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                for(int i=0; i<keys.size(); i++) {
                    Jedis jedis = JedisUtil.getJedis();
                    long resp = jedis.expire(keys.get(i), Constant.JedisConstant.LoopTime);

                    if(resp == 0L) {
                        keys.remove(keys.get(i));
                        i --;
                    }
                }
            }
        }, (Constant.JedisConstant.LoopTime-1) * 1000L);
    }
}
