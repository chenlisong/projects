package com.chenlisong.geohash;

import com.alibaba.fastjson.JSON;
import com.chenlisong.util.JedisUtil;
import redis.clients.jedis.GeoCoordinate;
import redis.clients.jedis.GeoRadiusResponse;
import redis.clients.jedis.GeoUnit;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.params.geo.GeoRadiusParam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GeoHash {

    private Jedis jedis;

    public GeoHash(Jedis jedis) {
        this.jedis = jedis;
    }

    public long addPosition(String key, String member, double x, double y) {
        return jedis.geoadd(key, x, y, member);
    }

    public long addPositions(String key, Position... positions) {
        Map<String, GeoCoordinate> map = new HashMap<>();
        for(Position position: positions) {
            map.put(position.member, new GeoCoordinate(position.x, position.y));
        }
        return jedis.geoadd(key, map);
    }

    public double distance(String key, String member1, String member2, GeoUnit unit) {
        return jedis.geodist(key, member1, member2, unit);
    }

    public List<GeoCoordinate> position(String key, String... member) {
        return jedis.geopos(key, member);
    }

    public List<GeoRadiusResponse> nearDesc(String key, String member, int distance, GeoUnit unit, int count) {
        return jedis.georadiusByMember(key, member, distance, unit, GeoRadiusParam.geoRadiusParam().sortDescending().count(count));
    }

    public static void main(String[] args) {
        Position aaa = new Position("aaa", 116.48105, 39.996794);
        Position bbb = new Position("bbb", 116.514203, 39.905409);
        Position ccc = new Position("ccc", 116.489033, 40.007669);

        String key = "cls:position:test";
        GeoHash geoHash = new GeoHash(JedisUtil.getJedis());
        geoHash.addPositions(key, aaa, bbb, ccc);

        System.out.println(geoHash.distance(key, "aaa", "bbb", GeoUnit.KM));

        System.out.println(geoHash.position(key, "aaa"));

        System.out.println(JSON.toJSONString(geoHash.nearDesc(key, "aaa", 3, GeoUnit.KM, 3)));
    }

    static class Position{
        String member;
        double x, y;
        public Position(String member, double x, double y) {
            this.member = member;
            this.x = x;
            this.y = y;
        }
    }

}
