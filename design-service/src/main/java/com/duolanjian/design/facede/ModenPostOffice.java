package com.duolanjian.design.facede;

/**
 * @author chenlisong
 *
 */
public class ModenPostOffice {

	private LetterProcess letterProcess = new LetterProcessImp();
	
	//写信，封装，投递，一体化了
	public void sendLetter(String content, String enveloper) {

		//首先要写信的内容
		letterProcess.writeContent(content);
		
		//其次写信封
		letterProcess.fileEnvelope(enveloper);
		
		//把信放到信封里
		letterProcess.letter2Envelope();
		
		//然后邮递
		letterProcess.sendLetter();
		
	}
	
}
