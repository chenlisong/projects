package com.chenlisong.delayqueue;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.chenlisong.util.JedisUtil;
import redis.clients.jedis.Jedis;

import java.lang.reflect.Type;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

/**
 * 延迟队列的实现
 */
public class DelayingQueue<T> {

    private String key;

    private Jedis jedis;

    // fastjson 序列化对象中存在 generic 类型时，需要使用 TypeReference
    private Type TaskType = new TypeReference<TaskItem<T>>() {
    }.getType();

    public DelayingQueue(Jedis jedis, String key) {
        this.key = key;
        this.jedis = jedis;
    }

    public void delay(T msg, int seconds) {
        TaskItem<T> taskItem = new TaskItem<T>();
        taskItem.id = UUID.randomUUID().toString();
        taskItem.msg = msg;
        String value = JSON.toJSONString(taskItem);
        jedis.zadd(key, System.currentTimeMillis()+(seconds * 1000L), value);
        System.out.println(String.format("produce msg: %s, delay time: %d", value, seconds));
    }

    public void loop(){

        loopFlag: while(true) {
            try{
                Set<String> values = jedis.zrangeByScore(key, 0, System.currentTimeMillis(), 0, 1);
                if(values.isEmpty()) {
                    Thread.sleep(500L);
                    continue loopFlag;
                }

                String value = values.iterator().next();
                if(jedis.zrem(key, value) > 0) {
                    TaskItem<T> taskItem = JSON.parseObject(value, TaskType);
                    System.out.println("consume msg: " + value);
                }
            }catch (Exception e) {
                e.printStackTrace();
                break loopFlag;
            }
        }
    }

    public static void main(String[] args) throws Exception{
        Jedis jedis = JedisUtil.getJedis();

        DelayingQueue<String> delayingQueue = new DelayingQueue(jedis, "cls:delayqueue");
        new Thread(() -> {
            Random random = new Random();
            for(int i=0; i<10; i++) {
                delayingQueue.delay("clstest:"+i, random.nextInt(5));
            }
        }).start();

        new Thread(() -> {
            delayingQueue.loop();
        }).start();

        while(true) {
            Thread.sleep(1000L);
        }
    }

    static class TaskItem<T> {

        public String id;

        public T msg;
    }

}
