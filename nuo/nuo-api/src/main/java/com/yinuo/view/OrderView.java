package com.yinuo.view;

import java.util.HashMap;

import com.yinuo.bean.Order;
import com.yinuo.bean.Student;
import com.yinuo.util.DateTool;

public class OrderView extends HashMap<String, Object> {

	private static final long serialVersionUID = 1L;

	public OrderView(Order order, Student student) {
		if(order == null) {
			return;
		}
		
		put("id", order.getId());
		put("userId", order.getUserId());
		put("studentId", order.getStudentId());
		put("goodId", order.getGoodId());
		put("state", order.getState());

		put("pay_time", DateTool.standardSdf.format(order.getPayTime()));
		put("begin_term_time", DateTool.standardSdf.format(order.getBeginTermTime()));
		put("create_time", DateTool.standardSdf.format(order.getCreateTime()));
		put("term_months", order.getTermMonths());
		
		put("student", student);
	}
}
