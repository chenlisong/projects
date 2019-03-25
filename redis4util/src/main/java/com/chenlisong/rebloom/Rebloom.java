package com.chenlisong.rebloom;

import com.chenlisong.util.JedisUtil;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 布隆过滤器
 */
public class Rebloom {

    private Jedis jedis;

    private int percent = 20, batchNumber = 100;

    private Random random = new Random();

    public Rebloom(Jedis jedis) {this.jedis = jedis;}

    public int bfadd(String key, String value) {

        String script = String.format("return redis.call('bf.add', '%s', '%s')", key, value);
        Object resp = jedis.eval(script);
        if(resp != null) {
            return Integer.parseInt(resp.toString());
        }
        return 0;
    }

    public void bfmadd(String key, String... values) {
        String initScript = String.format("return redis.call('bf.madd', '%s'", key);
        StringBuilder script = new StringBuilder(initScript);

        for(String value: values) {
            script.append(", '").append(value).append("'");
        }
        script.append(")");

        jedis.eval(script.toString());
    }

    public int bfexists(String key, String value) {
        String script = String.format("return redis.call('bf.exists', '%s', '%s')", key, value);

        Object resp = jedis.eval(script.toString());
        if(resp != null) {
            return Integer.parseInt(resp.toString());
        }
        return 0;
    }

    public void bfaddBatchRandomData(String key, int number) {
        int percentIndex = 0;
        int percentUnit = number/percent;

        List<String> elements = new ArrayList<String>();
        while(number-- > 0) {
            if(number % percentUnit == 0) {
                System.out.println(String.format("execute pfaddBatchRandomData key: %s, number: %d, percent: %d/%d",
                        key, number, ++percentIndex, percent));
            }

            if(elements.size() > batchNumber) {
                this.bfmadd(key, elements.toArray(new String[elements.size()]));
                elements = new ArrayList<>();
            }
            elements.add(""+random.nextInt());
        }

        if(elements.size() > 0) {
            this.bfmadd(key, elements.toArray(new String[elements.size()]));
        }
    }

    public static void main(String[] args) {
        String key = "cls:rebloom";

        Rebloom rebloom = new Rebloom(JedisUtil.getJedis());
        System.out.println(rebloom.bfadd(key, "user1"));
        System.out.println(rebloom.bfadd(key, "user2"));
        System.out.println(rebloom.bfadd(key, "user3"));
//
        rebloom.bfmadd(key, "user4", "user5");
//
        System.out.println(rebloom.bfexists(key, "user1"));
        System.out.println(rebloom.bfexists(key, "user4"));
        System.out.println(rebloom.bfexists(key, "user9"));

        rebloom.bfaddBatchRandomData(key+"1", 1000000);

        /**
         * 执行后rebloom1信息：Value at:0x7f4cd5c28940 refcount:1 encoding:raw serializedlength:11185124 lru:8095127 lru_seconds_idle:20
         * 大小11m
         * 场景可用于缓存防击穿，黑名单过滤等
         */

    }



}
