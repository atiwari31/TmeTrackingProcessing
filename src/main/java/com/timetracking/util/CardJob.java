package com.dcardprocessing.util;

import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.TriggerContext;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.dcardprocessing.bean.ScanSchedule;
import com.dcardprocessing.service.ScanScheduleService;

@EnableScheduling
@Configuration
@Component
public class CardJob implements SchedulingConfigurer {

	@Autowired
	private ScanScheduleService scanScheduleService;
	@Autowired
	private ScanScheduleUtility scanScheduleUtility;
	String cronsExpressions = null;
	
	@Override
	public void configureTasks(ScheduledTaskRegistrar scheduledTaskRegistrar) { 
		cronsExpressions=getCronExpressionFromDb();
		if(null!=cronsExpressions) {
		    Stream.of(StringUtils.split(cronsExpressions, "|")).forEach(cronExpression -> { 
	
		         Runnable runnable = () -> scanScheduleUtility.scanSchdule();
	
		                      Trigger trigger = new Trigger() {
		                       @Override
		                        public Date nextExecutionTime(TriggerContext triggerContext) {
		                            CronTrigger crontrigger = new CronTrigger(cronExpression);
		                            return crontrigger.nextExecutionTime(triggerContext);
		                        }
		                    };
		               scheduledTaskRegistrar.addTriggerTask(runnable, trigger);
		});
		}
	}
	//0 0 15 10 * * 20015 – Fire at 10:15am every day during the year 2015
	@Bean
	public String getCronExpressionFromDb() {
		// read here from database and return
		List<ScanSchedule> schduleList = scanScheduleService.getScheduleByDate();
		  long hrs =0l;
		  long min =0l;  
		  long sec =0l;
		  String cornExpressionFinal=null;
		  String corn="* * *";
		  int count=0;
		for(ScanSchedule scanSchedule :schduleList) {
				String seperateStr=null;
				String seperateStrLast="|";
			  String time= scanSchedule.getScanTime();
			  String[] arrOfStr=time.split(":");
			   hrs = Integer.parseInt(arrOfStr[0]);
			   min = Integer.parseInt(arrOfStr[1]);
			   sec = Integer.parseInt(arrOfStr[2]);
			   if(count>=1) {
					  seperateStr="|";
					  if(count<schduleList.size()-1) {
						  cornExpressionFinal=cornExpressionFinal + seperateStr+sec+" "+min+" "+hrs+" "+corn+"  "+seperateStrLast ;
					  }else {
						  cornExpressionFinal=cornExpressionFinal + seperateStr+sec+" "+min+" "+hrs+" "+corn;
					  }
					  
				  }else {
					  cornExpressionFinal=sec+" "+min+" "+hrs+" "+corn ;
				  }
			   count++;
		}
		System.out.println(">>>>>>>>>>>>"+cornExpressionFinal);
		
		return cornExpressionFinal;
	}



}
