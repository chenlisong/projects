package com.duolanjian.design.facede;
/**
 * 写信的具体实现了
 * @author chenlisong
 *
 */
public class LetterProcessImp implements LetterProcess{

	//写信
	public void writeContent(String content) {
		System.out.println("write content :"+content);
	}

	//在信封上填写必要的信息
	public void fileEnvelope(String address) {
		System.out.println("write envelope :"+address);
		
	}

	//把信放到信封中，并封好
	public void letter2Envelope() {
		System.out.println("put letter into envelope...");
	}

	//塞到邮箱中，邮递
	public void sendLetter() {
		System.out.println("send letter ...");
	}

	public void checkLetter(LetterProcess letterProcess) {
		System.out.println("check letter ...");
	}

}
