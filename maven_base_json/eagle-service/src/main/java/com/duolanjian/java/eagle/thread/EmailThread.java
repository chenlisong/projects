package com.duolanjian.java.eagle.thread;

import java.util.concurrent.CountDownLatch;

import com.duolanjian.java.eagle.util.EmailUtil;

public class EmailThread implements Runnable{

	private String email;
	
	private String title;
	
	private String content;
	
	private EmailUtil emailUtil;
	
	private CountDownLatch latch;
	
	public EmailThread(String email, String title, String content, EmailUtil emailUtil, CountDownLatch latch) {
		this.email = email;
		this.title = title;
		this.content = content;
		this.emailUtil = emailUtil;
		this.latch = latch;
	} 
	
	public void run() {
		try{
			emailUtil.sendEmail(email , title, content);
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			System.out.println("countDown");
			latch.countDown();
		}
	}

}
