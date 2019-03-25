package com.duolanjian.design.interpreter;

import java.util.HashMap;
import java.util.Stack;

public class Calculator {

	private Expression expression;
	
	public Calculator(String expStr) {
		
		Stack<Expression> stacks = new Stack<Expression>();
		
		char[] charArray = expStr.toCharArray();

		Expression left = null;
		Expression right = null;
		
		for(int i=0; i<charArray.length; i++) {
			
			switch (charArray[i]) {
			case '+':
				left = stacks.pop();
				right = new VarExpression(String.valueOf(charArray[++i]));
				stacks.push(new AddExpression(left, right));
				break;
			case '-':
				left = stacks.pop();
				right = new VarExpression(String.valueOf(charArray[++i]));
				stacks.push(new SubExpression(left, right));
				break;
			default:
				stacks.push(new VarExpression(String.valueOf(charArray[i])));
			}
		}
		this.expression = stacks.pop();
	}
	
	public int run(HashMap<String,Integer> var) {
		return this.expression.interpreter(var);
	}
	
}
