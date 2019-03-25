package com.chenlisong.limit;

import java.util.HashMap;
import java.util.Map;

/**
 * 漏斗限流
 */
public class LimitFunnel {

    //容量
    private int capacity;

    //流失的速度
    private float leakingRate;

    //剩余容量
    private int leftQuota;

    //上次流失的时间
    private long leakingTs;

    public LimitFunnel(){}

    public LimitFunnel(int capacity, float leakingRate) {
        this.capacity = capacity;
        this.leakingRate = leakingRate;
    }

    public void makeSpace() {
        long nowTime = System.currentTimeMillis();
        int deltaQuota = (int) ((nowTime - leakingTs) * leakingRate);
        //超出int值，
        if(deltaQuota < 0) {
            this.leftQuota = capacity;
            this.leakingTs = nowTime;
            return;
        }

        if(deltaQuota < 1) {
            return ;
        }

        this.leftQuota += deltaQuota;
        this.leakingTs = nowTime;
        if(this.leftQuota > this.capacity) {
            this.leftQuota = capacity;
        }
    }

    public boolean watering(int quota) {
        makeSpace();
        if(this.leftQuota >= quota) {
            this.leftQuota -= quota;
            return true;
        }
        return false;
    }

    private Map<String, LimitFunnel> funnels = new HashMap<>();

    public boolean isActionAllowed(String userId, String actionKey, int capacity, float leakingRate) {
        String key = String.format("cls:funnel:%s:%s", userId, actionKey);
        LimitFunnel funnel = funnels.get(key);
        if(funnel == null) {
            funnel = new LimitFunnel(capacity, leakingRate);
            funnels.put(key, funnel);
        }

        return funnel.watering(1);
    }

    public static void main(String[] args) {
        LimitFunnel funnel = new LimitFunnel();
        for(int i=0; i<20; i ++) {
            System.out.println(funnel.isActionAllowed("cls", "funnel", 5, 1));
        }
    }
}
