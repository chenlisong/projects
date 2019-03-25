package com.duolanjian.design.prototype;

import java.util.Random;

public class Client {

	public static void main(String[] args) {
		
		Mail src = new Mail(new AdvTemplate());
		
		int count = 6;
		
		for(int i=0; i<count; i++) {
			//浅clone，解决多线程访问不安全的问题
			Mail mail = src.clone();
			mail.setReceiver(getRandomString(9));
			mail.setTail(" to " + mail.getReceiver());
			sendMail(mail);
		}
	}
	
	public static void sendMail(Mail mail) {
		System.out.println("send to " + mail.getReceiver() + "@qq.com\t" + mail.getSubject() + "\t" +
				mail.getContent() + "\t" + mail.getTail());
	}
	
	public static String getRandomString(int number) {
		//防止拼错26个字母
		char[] msg = new char['z'-'a' + 1];
		for(int i='a'; i<='z'; i++) {
			msg[i - 'a'] = (char) i;
		}
		Random random = new Random();
		StringBuffer result = new StringBuffer();
		for(int i=0; i<number; i++) {
			result.append(msg[random.nextInt(msg.length)]);
		}
		
		return result.toString();
	}
}
