package com.chenlisong.bitfield;

import com.chenlisong.util.JedisUtil;
import redis.clients.jedis.Jedis;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 基于redis位图实现的签到功能
 */
public class BitFieldUtil {

    private Jedis jedis;

    private String key;

    public BitFieldUtil(Jedis jedis, String key) {
        this.jedis = jedis;
        this.key = key;
    }

    public void setbit(long offset) {
        jedis.setbit(key, offset, "1");
    }

    public int bitcount(long offset, int count) {
        int result = 0;
        long value = bitField(offset, count);

        while(value > 0) {
            if(value % 2 == 1) { result++; }
            value = value >> 1;
        }

        return result;
    }

    public boolean isBitvalue(long offset) {
        return "1".equals(jedis.getbit(key, offset));
    }

    public Map<Integer, Boolean> bitMap(long offset, int count) {
        Map<Integer, Boolean> result = new HashMap<>();
        long value = bitField(offset, count);
        int index = 1;

        while(index <= count) {
            boolean flag = false;
            if((value & 1) == 1) { flag = true; }
            value = value >> 1;
            result.put(count-index, flag);
            index ++;
        }
        return result;
    }

    public long bitField(long offset, int count) {
        List<Long> values = jedis.bitfield(key, "get", "u"+count, ""+offset);
        long value = values.iterator().next();
        return value;
    }

    public int reverseBinary(int value) {
        int length = Integer.toBinaryString(value).length();
        int result = 0;
        int unit = 1;
        for(int i=length-1; i>=0; i--) {

            if((value >> i & 1) == 1) {
                result += unit;
            }
            unit *= 2;
        }

        return result;
    }

    public long convert2Bit(String day) throws Exception{
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = sdf.parse(day);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        return calendar.get(Calendar.DAY_OF_YEAR) - 1;
    }

    public String convert2Day(int offset) throws Exception{
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_YEAR, offset+1);

        return sdf.format(calendar.getTime());
    }

    public void addDay(String day) throws Exception{
        long offset = convert2Bit(day);
        setbit(offset);
    }

    public void queryDay(String day, int count) throws Exception{
        long offset = convert2Bit(day);
        int number = bitcount(offset, count);

        System.out.println(String.format("count: %d", number));

        Map<Integer, Boolean> map = bitMap(offset, count);
        System.out.println("map: " + map);
        for(Integer key: map.keySet()) {
            System.out.println(String.format("day: %s, sign: %b", convert2Day(key+(int)offset), map.get(key)));
        }

    }

    public static void main(String[] args) throws Exception{
        Jedis jedis = JedisUtil.getJedis();
        BitFieldUtil util = new BitFieldUtil(jedis, "w");
        System.out.println(util.bitField(0, 4));

        System.out.println(util.bitcount(0, 4));

        System.out.println(util.bitMap(0, 4));

        System.out.println(Integer.toBinaryString(11));
        System.out.println(Integer.toBinaryString(util.reverseBinary(11)));

        util.addDay("2018-06-01");
        util.addDay("2018-06-03");
        util.addDay("2018-06-04");
        util.addDay("2018-06-07");
        util.queryDay("2018-06-02", 10);
    }

}
