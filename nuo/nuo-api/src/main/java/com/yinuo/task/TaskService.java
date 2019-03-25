package com.yinuo.task;

import java.util.concurrent.CountDownLatch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.yinuo.util.EmailUtil;

@Component
public class TaskService {

	
	@Autowired
	private EmailUtil emailUtil;
	
	@Scheduled(cron="0/1 * * * * * ")
    public void taskCycle(){
		while(true) {
			try {
				CountDownLatch latch = new CountDownLatch(15);
				for(int i=0; i<15 ;i++) {
			        System.out.println("spring scheduled execute ... count:"+i);
				}
				latch.await();
				System.out.println("end");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
}
