package com.chenlisong.eatqueue;

import com.chenlisong.util.JedisUtil;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.List;

/**
 * 两个窗口，学生m，领导n。
 排队的时候学生能进领导的队，但是当领导来了，如果没位置，需要把学生踢出去
 */
public class StudentAndTeacherQueue {

    private static Jedis jedis = JedisUtil.ceateProxtJedis();

    private static final String studentQueue = "cls:studentQueue";

    private static final String teacherQueue = "cls:teacherQueue";

    private static final long studentQueueSize = 5;

    private static final long teacherQueueSize = 2;

    public static void main(String[] args) {

        StudentAndTeacherQueue queue = new StudentAndTeacherQueue();
        queue.studentPush("s1");
        queue.studentPush("s2");
        queue.studentPush("s3");
        queue.studentPush("s4");
        queue.studentPush("s5");
        queue.studentPush("s6");
        queue.teacherPush("t1");
        queue.teacherPush("t2");
        queue.teacherPush("t3");

    }

    public void studentPush(String studentId) {
        this.studentPush(studentId, true);
    }

    public void studentPush(String studentId, boolean teacherFirst) {

        if(teacherFirst && jedis.llen(teacherQueue) < teacherQueueSize) {
            jedis.lpush(teacherQueue, studentId);
            System.out.println("teacher queue add: " + studentId);
        }else if(jedis.llen(studentQueue) < studentQueueSize){
            jedis.lpush(studentQueue, studentId);
            System.out.println("student queue add: " + studentId);
        }else {
            System.out.println("student queue is full, refused request.");
            return;
        }
    }

    public void teacherPush(String teacherId) {
        if(jedis.llen(teacherQueue) < teacherQueueSize) {
            jedis.lpush(teacherQueue, teacherId);
        }else {
            List<String> teacherIds = new ArrayList<>();

            String tempId = null;
            while((tempId = jedis.lpop(teacherQueue)) != null) {
                teacherIds.add(tempId);
            }

            boolean isFindStudentFlag = false;
            for(String temp: teacherIds) {
                if(!isFindStudentFlag && temp.contains("s")) {
                    studentPush(temp, false);
                    isFindStudentFlag = true;
                }else {
                    teacherPush_(temp);
                }
            }

            if(isFindStudentFlag) {
                jedis.lpush(teacherQueue, teacherId);
            }else {
                System.out.println("teacher is full, refused request.");
            }

        }
    }

    public void teacherPush_(String teacherId) {
        if(jedis.llen(teacherQueue) < teacherQueueSize){
            jedis.lpush(teacherQueue, teacherId);
        }else {
            System.out.println("teacher is full, refused request.");
        }
    }

    public void studentPop() {
        String studentId = jedis.lpop(studentQueue);
        if(studentId != null) {
            System.out.println("pop student queue: " + studentId);
        }
    }

    public void teacherPop() {
        String teacherId = jedis.lpop(teacherQueue);
        if(teacherId != null) {
            System.out.println("pop teacher queue: " + teacherId);
        }
    }
}
