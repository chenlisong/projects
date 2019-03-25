package gs.scheduling.task;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTasks {
	
	private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);
	
	private static final SimpleDateFormat sdf = new SimpleDateFormat("hh:MM:ss");
	
	@Scheduled(fixedRate=5000)
	public void print() {
		
		log.info("the time is now{}", sdf.format(new Date()));
		
	}
	

}
