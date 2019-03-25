package com.duolanjian.design.factory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.duolanjian.design.util.ClassUtils;

/**
 * I'm glad to share my knowledge with you all.
 * 今天讲女娲造人的故事，这个故事梗概是这样的：
 * 很久很久以前，盘古开辟了天地，用身躯造出日月星辰、山川草木，天地一片繁华
 * One day，女娲下界走了一遭，哎！太寂寞，太孤独了，没个会笑的、会哭的、会说话的东东
 * 那怎么办呢？不用愁，女娲，神仙呀，造出来呀，然后捏泥巴，放八卦炉（后来这个成了太白金星的宝贝）
 * 中烤，于是就有了人：
 * 我们把这个生产人的过程用Java程序表现出来：
 * @author chenlisong
 *
 */
@SuppressWarnings("all")
public class HumanFactory {
	
	private static Map<String, Human> humanMap = new HashMap<String, Human>();

	public static Human createHuman(Class<? extends Human> c) {
		Human human = null;
		try {
			
			if(humanMap.get(c.getSimpleName()) != null) {
				human = humanMap.get(c.getSimpleName());
			}else {
				human = (Human)Class.forName(c.getName()).newInstance();
				humanMap.put(c.getSimpleName(), human);
			}
		} catch (Exception e) {
			
			System.out.println("造人出现问题");
		}
		
		return human;
		
	}

	public static Human createHuman() {
		
		List<Class> createHumanList =
				ClassUtils.getAllClassByInterface(Human.class); //定义了多少人类
		
		Human human = null;
		
		Random random = new Random();
		int rand = random.nextInt(createHumanList.size());
		
		human = HumanFactory.createHuman((Class<? extends Human>) createHumanList.get(rand));
		
		return human;
		
	}
	
	
}
