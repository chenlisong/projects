package com.duolanjian.java.eagle.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.duolanjian.java.eagle.thread.ThreadPool;

@Component
public class TaskService {

	
	@Autowired
	private ThreadPool threadPool;
	
	@Scheduled(cron="0/1 * * * * * ")
    public void taskCycle(){
		System.out.println("123");
	}
	
}
