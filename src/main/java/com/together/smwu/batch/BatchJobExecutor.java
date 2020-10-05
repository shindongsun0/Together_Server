package com.together.smwu.batch;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.configuration.JobLocator;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.NoSuchJobException;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@DisallowConcurrentExecution
public class BatchJobExecutor implements org.quartz.Job {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final JobLocator jobLocator;
    private final JobLauncher jobLauncher;

    @Autowired
    public BatchJobExecutor(JobLocator jobLocator, JobLauncher jobLauncher) {
        this.jobLocator = jobLocator;
        this.jobLauncher = jobLauncher;
    }

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        try {
            String jobName = BatchHelper.getJobName(context.getMergedJobDataMap());
            logger.info("[{}] started.", jobName);
            JobParameters jobParameters = BatchHelper.getJobParameters(context);
            jobLauncher.run(jobLocator.getJob(jobName), jobParameters);
            logger.info("[{}] completed.", jobName);
        } catch (NoSuchJobException | JobExecutionAlreadyRunningException | JobRestartException
                | JobInstanceAlreadyCompleteException | JobParametersInvalidException | SchedulerException e) {
            logger.error("job execution exception! - {}", e.getCause());
            throw new JobExecutionException();
        }
    }
}
