package com.jckim.www;

import java.util.Date;
import java.util.Map;
import java.util.Map.Entry;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.configuration.JobLocator;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.NoSuchJobException;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.scheduling.quartz.QuartzJobBean;

public class JobLauncherDetails extends QuartzJobBean {
	
	static final String JOB_NAME = "jobName";
	
	private JobLocator jobLocator;
	private JobLauncher jobLauncher;
	

	
	
	public void setJobLocator(JobLocator jobLocator) {
		this.jobLocator = jobLocator;
	}




	public void setJobLauncher(JobLauncher jobLauncher) {
		this.jobLauncher = jobLauncher;
	}




	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		Map<String, Object> jobDataMap = context.getMergedJobDataMap();
		
		String jobName = (String)jobDataMap.get(JOB_NAME);
		
		jobDataMap.put("schedule.time", new Date(System.currentTimeMillis()));
		
		JobParametersBuilder builder = new JobParametersBuilder();
		
		for(Entry<String,Object> entry : jobDataMap.entrySet()){
			String key = entry.getKey();
			Object value = entry.getValue();
			if(value instanceof String && !key.equals(JOB_NAME)){
				builder.addString(key, (String)value);
			} else if(value instanceof Float && value instanceof Double){
				builder.addDouble(key, ((Number) value).doubleValue());
			} else if(value instanceof Integer && value instanceof Long){
				builder.addLong(key, ((Number) value).longValue());
			} else if(value instanceof Date){
				builder.addDate(key, (Date) value);
			}
			
		}
		
		JobParameters jobParameter = builder.toJobParameters();
		
		try {
			jobLauncher.run(jobLocator.getJob(jobName), jobParameter);
		} catch (JobExecutionAlreadyRunningException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JobRestartException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JobInstanceAlreadyCompleteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JobParametersInvalidException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchJobException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
