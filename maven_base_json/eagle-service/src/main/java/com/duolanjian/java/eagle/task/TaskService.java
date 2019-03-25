package com.duolanjian.java.eagle.task;

import java.util.concurrent.CountDownLatch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.duolanjian.java.eagle.thread.EmailThread;
import com.duolanjian.java.eagle.thread.ThreadPool;
import com.duolanjian.java.eagle.util.EmailUtil;

@Component
public class TaskService {

	
	@Autowired
	private EmailUtil emailUtil;
	
	@Autowired
	private ThreadPool threadPool;
	
	@Scheduled(cron="0/1 * * * * * ")
    public void taskCycle(){
		while(true) {
			try {
				CountDownLatch latch = new CountDownLatch(15);
				for(int i=0; i<15 ;i++) {
					threadPool.addThread(new EmailThread("787625718@qq.com", "test email " + i, 
							"hi。 this is test.", emailUtil, latch));
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
