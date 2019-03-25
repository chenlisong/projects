package com.cls.twosum;

import com.alibaba.fastjson.JSON;

class Solution {

    public static void main(String[] args) {
        System.out.println(JSON.toJSONString(new Solution().twoSum(new int[]{2, 7, 11, 15}, 9)));
        System.out.println(JSON.toJSONString(new Solution().twoSum(new int[]{0,4,3,0}, 0)));
    }

    public int[] twoSum(int[] nums, int target) {
        for(int i=0; i<nums.length-1; i++) {
            for(int j=i+1; j<nums.length; j++) {
                if(nums[i] + nums[j] == target) {
                    return new int[]{i, j};
                }
            }
        }
        throw new IllegalArgumentException("no such two num");
    }

    public int[] twoSumSelf(int[] nums, int target) {
        int[] result = new int[2];
        if(nums.length > 0) {

            sumLoop: for(int i=0; i<nums.length-1; i++) {
                for(int j=i+1; j<nums.length; j++) {
                    if(nums[i] + nums[j] == target) {
                        result[0] = i;
                        result[1] = j;
                        break sumLoop;
                    }
                }
            }
        }
        return result;
    }
}