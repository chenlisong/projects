package com.chenlisong.reentrantlock;

import com.chenlisong.util.JedisUtil;
import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.Map;

/**
 * 实现一个可重入锁，不考虑expire time
 */
public class Reentrantlock {

    private ThreadLocal<Map<String, Integer>> lockers = new ThreadLocal<Map<String, Integer>>();

    private Jedis jedis;

    public Reentrantlock(Jedis jedis) {
        this.jedis = jedis;
    }

    public boolean _lock(String key) {
        return "OK".equals(jedis.set(key, "1", "nx"));
    }

    public void _unlock(String key) {
        jedis.del(key);
    }

    private Map<String, Integer> currentLockers() {
        Map<String, Integer> refs = lockers.get();
        if(refs != null) {
            return refs;
        }

        lockers.set(new HashMap<String, Integer>());
        return lockers.get();
    }

    public boolean lock(String key) {
        Map<String, Integer> refs = currentLockers();
        Integer count = refs.get(key);
        if(count != null) {
            refs.put(key, count+1);
            return true;
        }

        if(this._lock(key)) {
            refs.put(key, 1);
            return true;
        }

        return false;
    }

    public boolean unlock(String key) {
        Map<String, Integer> refs = currentLockers();
        Integer count = refs.get(key);
        if(count == null) {
            return false;
        }

        count -= 1;
        if(count > 0) {
            refs.put(key, count);
        }else {
            refs.remove(key);
            this._unlock(key);
        }

        return true;
    }

    public static void main(String[] args) {
        Jedis jedis = JedisUtil.getJedis();
        Reentrantlock lock = new Reentrantlock(jedis);
        String key = "cls:reentrantlock";

        System.out.println(lock.lock(key));
        System.out.println(lock.lock(key));
        System.out.println(lock.unlock(key));
        System.out.println(lock.unlock(key));
    }

}
