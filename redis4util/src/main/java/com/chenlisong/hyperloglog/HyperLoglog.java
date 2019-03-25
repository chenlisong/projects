package com.chenlisong.hyperloglog;


import com.chenlisong.util.JedisUtil;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 分桶2的14次方，16384，每个桶占用6位，总共12K内存。
 *
 * 但是如果数据够少时，redis采用稀疏矩阵存储，无需关心性能。
 *
 * 求平均值采用的是倒数求和，更加真实
 *
 * 原理：给定一个随机整数，记录下来低位连续零位的最大长度k，通过k值可以估算出随机整数的数量。
 */
public class HyperLoglog {

    private Jedis jedis;

    private Random random = new Random();

    private int batchNumber = 100, percent = 20;

    public HyperLoglog(Jedis jedis) {
        this.jedis = jedis;
    }

    public long _pfadd(String key, String... elements) {
        return jedis.pfadd(key, elements);
    }

    public long _pfcount(String... key) {
        return jedis.pfcount(key);
    }

    public String _pfmerge(String destKey, String... sourceKeys) {
        return jedis.pfmerge(destKey, sourceKeys);
    }

    public void pfaddBatchRandomData(String key, int number) {
        int percentIndex = 0;
        int percentUnit = number/percent;

        List<String> elements = new ArrayList<String>();
        while(number-- > 0) {
            if(number % percentUnit == 0) {
                System.out.println(String.format("execute pfaddBatchRandomData key: %s, number: %d, percent: %d/%d",
                        key, number, ++percentIndex, percent));
            }

            if(elements.size() > batchNumber) {
                this._pfadd(key, elements.toArray(new String[elements.size()]));
                elements = new ArrayList<>();
            }
            elements.add(""+random.nextInt());
        }

        if(elements.size() > 0) {
            this._pfadd(key, elements.toArray(new String[elements.size()]));
        }
    }

    public static void main(String[] args) {
        String key = "cls:hyperloglog";
        String key1 = "cls:hyperloglog3";
        String key2 = "cls:hyperloglog4";

        HyperLoglog log = new HyperLoglog(JedisUtil.getJedis());
        System.out.println(log._pfadd(key, "user1", "user2", "user3"));

        System.out.println(log._pfcount(key));

        log.pfaddBatchRandomData(key1, 1000000);
        log.pfaddBatchRandomData(key2, 1000000);
        System.out.println(log._pfcount(key1));
        System.out.println(log._pfcount(key2));
        System.out.println(log._pfmerge(key1, key2));
        System.out.println(log._pfcount(key1));

    }

}