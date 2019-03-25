package com.duolanjian.design.advance;

public abstract class Handler {

	//对应的级别，1父亲，2丈夫，3儿子
	private int type;
	
	//如果该环节无法处理，那么下一个节点处理
	private Handler next;
	
	public Handler(int _type) {
		this.type = _type;
	}
	
	public void setNext(Handler _next) {
		this.next = _next;
	}
	
	public void handleMessage(Woman woman) {
		if(woman.getType() == this.type) {
			this.response(woman);
		}else {
			if(next != null) {
				this.next.handleMessage(woman);
			}else {
				System.out.println("没人了，无法传递");
			}
		}
	}
	
	public abstract void response(Woman woman);
}
