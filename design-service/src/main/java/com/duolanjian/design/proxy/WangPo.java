package com.duolanjian.design.proxy;

public class WangPo implements KindWomen{
	
	private KindWomen kindWomen;
	
	//王婆代理别的女人
	public WangPo (KindWomen kindWomen) {
		this.kindWomen = kindWomen;
	}

	//王婆年龄大，代理别人抛媚眼
	public void makeEyesWithMan() {
		this.kindWomen.makeEyesWithMan();
	}

	//王婆年龄大，找年轻的代替之
	public void makeHappy() {
		this.kindWomen.makeHappy();
	}

}
