package com.yinuo.task;

import com.alibaba.fastjson.JSON;
import com.yinuo.service.*;
import com.yinuo.util.CommonUtil;
import com.yinuo.util.DateTool;
import org.omg.CORBA.COMM_FAILURE;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class TaskService {


	private static final long sleepTimes = 1000L * 60 * 10;

	private Logger logger = LoggerFactory.getLogger(TaskService.class);
	
	@Scheduled(cron="0/1 * * * * * ")
    public void taskCycle() throws Exception{

		int scoreBatchCount = 0;
		int examStatCount = 0;
		do {
			logger.info("execute taskCycle");
			examStatCount = examStat();

			if(scoreBatchCount == 0 && examStatCount == 0) {
				Thread.sleep(sleepTimes);
			}
		}while(true);
	}

	/**
	 * 统计学生总分，学校班级rank
	 * @return
	 */
	private int examStat() {


		return 0;
	}
}
