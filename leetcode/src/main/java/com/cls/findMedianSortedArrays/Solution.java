package com.cls.findMedianSortedArrays;

public class Solution {

    public static void main(String[] args) {
        System.out.println(new Solution().findMedianSortedArrays(new int[]{1,3}, new int[]{2}));
    }

    public double findMedianSortedArrays(int[] nums1, int[] nums2) {

        int x = (nums1.length + nums2.length)%2 == 0 ?
                (nums1.length + nums2.length)/2-1 : (nums1.length + nums2.length)/2, y=(nums1.length + nums2.length)%2 == 0 ? x + 1: x;

        int valueX = 0, valueY = 0;

        int i = 0, j = 0;
        while(i < nums1.length || j < nums2.length) {

            int temp = 0;
            if(i< nums1.length && (j >= nums2.length || nums1[i] < nums2[j])) {
                temp = nums1[i];
                i++;
            }else {
                temp = nums2[j];
                j++;
            }

            if(i + j -1 == x) {
                valueX = temp;
            }

            if(i + j - 1 == y) {
                valueY = temp;
                break;
            }
        }

        return (valueX + valueY) * 1.0d/2;

    }
}
