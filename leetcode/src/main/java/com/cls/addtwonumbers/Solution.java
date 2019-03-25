package com.cls.addtwonumbers;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;

class Solution {

    public static void main(String[] args) {
        ListNode node1 = new ListNode(5);
//        node1.next = new ListNode(4);
//        node1.next.next = new ListNode(3);
//
        ListNode node2 = new ListNode(5);
//        node2.next = new ListNode(6);
//        node2.next.next = new ListNode(4);

        System.out.println(JSON.toJSONString(new Solution().addTwoNumbers(node1, node2)));
    }

    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {

        ListNode first = null;
        ListNode before = null;
        int addval = 0;

        while(l1 != null || l2 != null || addval > 0) {
            int x = l1 == null ? 0: l1.val;
            int y = l2 == null ? 0: l2.val;

            if(first == null) {
                first = new ListNode((x + y + addval) % 10);
                before = first;
            }else {
                before.next = new ListNode((x + y + addval) % 10);
                before = before.next;
            }

            addval = x + y + addval >= 10 ? 1 : 0;

            if(l1 != null) {l1 = l1.next;}
            if(l2 != null) {l2 = l2.next;}
        }
        return first;
    }
}

class ListNode implements Serializable{

    int val;

    ListNode next;

    ListNode(int x) {
        val = x;
    }

    @Override
    public String toString() {
        if(next != null) {
            return "val: " + val + ", " + next.toString();
        }else {
            return "val: " + val;
        }
    }
}