package com.duolanjian.design.interpreter;

import java.util.HashMap;

public class VarExpression extends Expression{

	private String key;
	
	public VarExpression(String _key) {
		this.key = _key;
	}
	
	@Override
	public int interpreter(HashMap<String, Integer> var) {
		return var.get(this.key);
	}

}
