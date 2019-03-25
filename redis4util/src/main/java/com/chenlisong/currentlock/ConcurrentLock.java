package com.chenlisong.currentlock;

import com.chenlisong.util.JedisUtil;
import redis.clients.jedis.Jedis;

import java.util.Random;

/**
 * 需要设置expire time的实现方式
 */
public class ConcurrentLock {

    private static final ThreadLocal<String> lockRandomValue = new ThreadLocal<String>();

    private static final Random random = new Random();

    private static final Jedis jedis = JedisUtil.getJedis();

    /**
     *
     * @param args
     */
    public static void main(String[] args) throws Exception{

        String key = "lock:chenlisong";

        //testSingleThread(key, 3);

        testMulThread(key, 3);

    }

    public static void testMulThread(String key, int timeout) throws Exception{

        for(int i=0; i<10; i++) {
            Runnable runnable = () -> {
                try{
                    dosomethingWithLock("lock:chenlisong", timeout, () -> {
                        try{
                            Thread.sleep(random.nextInt(5) * 1000L);
                            System.out.println(String.format("%s dosomething businesss ...", Thread.currentThread().getName()));
                        }catch (Exception e) {}
                    });
                }catch (Exception e) {
                    e.printStackTrace();
                }
            };

            new Thread(runnable).start();
        }
    }

    public static void testSingleThread(String key, int timeout) throws Exception{

        dosomethingWithLock("lock:chenlisong", timeout, () -> {
            System.out.println(String.format("%s dosomething businesss1 ...", Thread.currentThread().getName()));
        });

        dosomethingWithLock("lock:chenlisong", timeout, () -> {
            System.out.println(String.format("%s dosomething businesss2 ...", Thread.currentThread().getName()));
        });

        dosomethingWithLock("lock:chenlisong", timeout, () -> {
            System.out.println(String.format("%s dosomething businesss3 ...", Thread.currentThread().getName()));
        });
    }

    public static void dosomethingWithLock(String key, int timeout, RedisLockCallBack callBack) throws Exception{
        lockLoop(key, timeout);

        callBack.work();
        unlock(key);
    }

    public static void lockLoop(String key, int timeout) throws Exception{
        while(!lock(key, timeout)) {
            //System.out.println(String.format("%s not get lock key, sleep 1s, try again", Thread.currentThread().getName()));
            Thread.sleep(1000L);
        }
    }

    public static boolean lock(String key, int timeout) {
        lockRandomValue.set(random.nextInt()+"");
        String result = jedis.set(key, lockRandomValue.get()+"", "nx", "ex", timeout);
        if("OK".equals(result)) {
            System.out.println(String.format("%s lock success, randome: %s", Thread.currentThread().getName(), lockRandomValue.get()));
            return true;
        }

        return false;
    }

    public static boolean unlock(String key) {
        String value = jedis.get(key);
        if(value != null && value.equals(lockRandomValue.get())) {
            jedis.del(key);
            System.out.println(String.format("%s unlock success", Thread.currentThread().getName()));
        }else {
            System.out.println(String.format("%s unlock fail, not match self thread value. random: %s, redis value: %s",
                    Thread.currentThread().getName(), lockRandomValue.get(), value));
        }
        return true;
    }
}
