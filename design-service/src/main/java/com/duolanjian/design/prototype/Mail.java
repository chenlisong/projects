package com.duolanjian.design.prototype;

import java.util.ArrayList;

public class Mail implements Cloneable{

	private String subject;
	
	private String content;
	
	private String receiver;
	
	private String tail;
	
	private ArrayList<String> test = new ArrayList<String>();
	
	public Mail(AdvTemplate _template) {
		this.subject = _template.getSubject();
		this.content = _template.getContent();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Mail clone() {
		Mail mail = null;
		
		try {
			//浅clone，浅clone时String类型的可以被clone，而对象类型属性则不可以
			mail = (Mail) super.clone();
			//深clone，可以clone对象属性
			mail.test = (ArrayList<String>) this.test.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return mail;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public String getTail() {
		return tail;
	}

	public void setTail(String tail) {
		this.tail = tail;
	}

}
