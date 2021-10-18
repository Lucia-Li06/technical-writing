package com.hydsoft.monintors.config;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.stereotype.Component;

import com.hydsoft.monintors.tasks.SendMsgToEdgeXTask;

//@EnableScheduling
@Component
@Configuration
public class SchedulerConfig implements SchedulingConfigurer{
	
	@Value(value= "${TaskInterval}")
	private int taskInterval;
	
	@Autowired
	SendMsgToEdgeXTask sendMsgToEdgeXTask;
	
	public ThreadPoolTaskScheduler poolScheduler() {
		ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
		scheduler.setThreadNamePrefix("TaskScheduler");
		scheduler.setPoolSize(4);
		scheduler.setAwaitTerminationMillis(600);
		scheduler.setWaitForTasksToCompleteOnShutdown(true);
		scheduler.initialize();
		return scheduler;
	}

	@Override
	public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
//		taskRegistrar.setScheduler(poolScheduler());
//		taskRegistrar.addTriggerTask(
//				() -> sendMsgToEdgeXTask.send(), 
//				t -> {
//					Calendar nextExecutionTime = new GregorianCalendar();
//					Date lastExecutionTime = t.lastActualExecutionTime();
//					nextExecutionTime.setTime(lastExecutionTime != null ? lastExecutionTime : new Date());
//					nextExecutionTime.add(Calendar.MILLISECOND, taskInterval);
//					return nextExecutionTime.getTime();
//				});
		
	}

}
