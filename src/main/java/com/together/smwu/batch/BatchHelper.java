package com.together.smwu.batch;

import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.SchedulerException;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.util.StringUtils;

import java.util.*;

public class BatchHelper {
    private static final String JOB_NAME = "job";
    private static final String JOB_PARAMETERS_NAME_BY_CONFIG = "jobParameters";
    private static final String JOB_PARAMETERS_NAME_BY_TRIGGER = "triggerJobParameters";
    private static final String JOB_PARAMETERS_INSTANCE_ID = "InstanceId";
    private static final String JOB_PARAMETERS_TIMESTAMP = "timestamp";
    private static final List<String> KEYWORDS = Arrays.asList(JOB_NAME, JOB_PARAMETERS_NAME_BY_CONFIG);

    public static JobDetailFactoryBeanBuilder jobDetailFactoryBeanBuilder() {
        return new JobDetailFactoryBeanBuilder();
    }

    public static CronTriggerFactoryBeanBuilder cronTriggerFactoryBeanBuilder() {
        return new CronTriggerFactoryBeanBuilder();
    }

    public static String getJobName(JobDataMap jobDataMap) {
        return (String) jobDataMap.get(JOB_NAME);
    }

    public static JobParameters getJobParameters(JobExecutionContext context) throws SchedulerException {
        JobDataMap jobDataMap = context.getMergedJobDataMap();
        return new JobParametersBuilder(
                getMergedJobParameters(
                        (JobParameters) jobDataMap.get(JOB_PARAMETERS_NAME_BY_CONFIG),
                        (JobParameters) jobDataMap.get(JOB_PARAMETERS_NAME_BY_TRIGGER)
                )
        )
                .addString(JOB_PARAMETERS_INSTANCE_ID, context.getScheduler().getSchedulerInstanceId())
                .addLong(JOB_PARAMETERS_TIMESTAMP, System.currentTimeMillis())
                .toJobParameters();
    }

    private static JobParameters getMergedJobParameters(JobParameters jobParameters1, JobParameters jobParameters2) {
        Map<String, JobParameter> merged = new HashMap<>();
        merged.putAll(!StringUtils.isEmpty(jobParameters1) ? jobParameters1.getParameters() : Collections.EMPTY_MAP);
        merged.putAll(!StringUtils.isEmpty(jobParameters2) ? jobParameters2.getParameters() : Collections.EMPTY_MAP);
        return new JobParameters(merged);
    }

    public static class JobDetailFactoryBeanBuilder {

        boolean durability = true;
        boolean requestsRecovery = true;
        private Map<String, Object> map;
        private JobParametersBuilder jobParametersBuilder;

        JobDetailFactoryBeanBuilder() {
            this.map = new HashMap<>();
            this.jobParametersBuilder = new JobParametersBuilder();
        }

        public JobDetailFactoryBeanBuilder job(Job job) {
            this.map.put(JOB_NAME, job.getName());
            return this;
        }

        public JobDetailFactoryBeanBuilder durability(boolean durability) {
            this.durability = durability;
            return this;
        }

        public JobDetailFactoryBeanBuilder requestsRecovery(boolean requestsRecovery) {
            this.requestsRecovery = requestsRecovery;
            return this;
        }

        public JobDetailFactoryBeanBuilder parameter(String key, Object value) {
            if (KEYWORDS.contains(key)) {
                throw new RuntimeException("Invalid Parameter.");
            }
            this.addParameter(key, value);
            return this;
        }

        private void addParameter(String key, Object value) {
            if (value instanceof String) {
                this.jobParametersBuilder.addString(key, (String) value);
                return;
            } else if (value instanceof Float || value instanceof Double) {
                this.jobParametersBuilder.addDouble(key, ((Number) value).doubleValue());
                return;
            } else if (value instanceof Integer || value instanceof Long) {
                this.jobParametersBuilder.addLong(key, ((Number) value).longValue());
                return;
            } else if (value instanceof Date) {
                this.jobParametersBuilder.addDate(key, (Date) value);
                return;
            } else if (value instanceof JobParameter) {
                this.jobParametersBuilder.addParameter(key, (JobParameter) value);
                return;
            }
            throw new RuntimeException("Not Supported Parameter Type.");
        }

        public JobDetailFactoryBean build() {
            if (!map.containsKey(JOB_NAME)) {
                throw new RuntimeException("Not Found Job Name.");
            }
            map.put(JOB_PARAMETERS_NAME_BY_CONFIG, jobParametersBuilder.toJobParameters());

            JobDetailFactoryBean jobDetailFactory = new JobDetailFactoryBean();
            jobDetailFactory.setJobClass(BatchJobExecutor.class);
            jobDetailFactory.setDurability(this.durability);
            jobDetailFactory.setRequestsRecovery(this.requestsRecovery);
            jobDetailFactory.setJobDataAsMap(this.map);
            return jobDetailFactory;
        }
    }

    public static class CronTriggerFactoryBeanBuilder {
        private String name;
        private String cronExpression;
        private JobDetailFactoryBean jobDetailFactoryBean;

        public CronTriggerFactoryBeanBuilder name(String name) {
            this.name = name;
            return this;
        }

        public CronTriggerFactoryBeanBuilder cronExpression(String cronExpression) {
            this.cronExpression = cronExpression;
            return this;
        }

        public CronTriggerFactoryBeanBuilder jobDetailFactoryBean(JobDetailFactoryBean jobDetailFactoryBean) {
            this.jobDetailFactoryBean = jobDetailFactoryBean;
            return this;
        }

        public CronTriggerFactoryBean build() {
            if (this.cronExpression == null || this.jobDetailFactoryBean == null) {
                throw new RuntimeException("cronExpression and jobDetailFactoryBean is required.");
            }
            CronTriggerFactoryBean cronTriggerFactoryBean = new CronTriggerFactoryBean();
            cronTriggerFactoryBean.setName(this.name);
            cronTriggerFactoryBean.setJobDetail(this.jobDetailFactoryBean.getObject());
            cronTriggerFactoryBean.setCronExpression(this.cronExpression);
            return cronTriggerFactoryBean;
        }
    }
}
