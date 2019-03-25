package com.duolanjian.java.eagle.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.stereotype.Component;

@Component
public class ThreadPool {

	private ExecutorService es; 
	
	public ThreadPool() {
        es = Executors.newCachedThreadPool();
    }

	public void addThread(Runnable thread) {
		es.execute(thread);
	}
}
