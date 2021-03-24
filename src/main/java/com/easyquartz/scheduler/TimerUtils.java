package com.easyquartz.scheduler;

import org.quartz.*;

import java.util.Date;

public class TimerUtils {

    private TimerUtils(){}

    public static JobDetail buildJobDetail(final Class jobClass, final String cronJobExpression) {
        final JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put(jobClass.getSimpleName(), cronJobExpression);

        return JobBuilder
                .newJob(jobClass)
                .withIdentity(jobClass.getSimpleName())
                .setJobData(jobDataMap)
                .requestRecovery(true)
                .build();
    }

    public static Trigger buildTrigger(final Class jobClass, final String cronJobExpression) {
        return TriggerBuilder
                .newTrigger()
                .withIdentity(jobClass.getSimpleName())
                .withSchedule(CronScheduleBuilder.cronSchedule(cronJobExpression)
                .withMisfireHandlingInstructionIgnoreMisfires())
                .startAt(new Date(System.currentTimeMillis()))
                .build();
    }
}
