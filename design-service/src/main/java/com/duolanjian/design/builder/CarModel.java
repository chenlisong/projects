package com.duolanjian.design.builder;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;

public abstract class CarModel {
	
	private List<String> sequence;

	public abstract void start();

	public abstract void enginBoom();

	public abstract void alarm();

	public abstract void stop();

	public void run() {
		if(CollectionUtils.isNotEmpty(sequence)) {
			for(String action : sequence) {
				if("start".equals(action)) {
					this.start();
				}else if("enginBoom".equals(action)) {
					this.enginBoom();
				}else if("alarm".equals(action)) {
					this.alarm();
				}else if("stop".equals(action)) {
					this.stop();
				}
			}
		}
		
	}
	
	public void setSequence(List<String> sequence) {
		this.sequence = sequence;
	}
	
}
