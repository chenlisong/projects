package com.duolanjian.design.factory_abstract;

/**
 * 编写一个抽象类，根据enum创建一个人类出来
 * @author chenlisong
 *
 */
public abstract class AbstractHumanFactory implements HumanFactory{

	public Human createHuman(HumanEnum humanEnum) {
		Human human = null;
		
		if(!"".equals(humanEnum.getValue())) {
			try {
				human = (Human) Class.forName(humanEnum.getValue()).newInstance();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return human;
		
	}
	
}
