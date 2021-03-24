package com.easyquartz.scheduler;

import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Component
public class ScheduleService {
    private final Scheduler scheduler;

    @Autowired
    public ScheduleService(Scheduler scheduler){
        this.scheduler = scheduler;
    }

    @PostConstruct
    public void init(){
        try {
            scheduler.start();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    public <T extends Job> void schedule(final Class<T> jobClass, final String groupID, final String cronJobExpression) {
        final JobDetail jobDetail = TimerUtils.buildJobDetail(jobClass, groupID, cronJobExpression);
        final Trigger trigger = TimerUtils.buildTrigger(jobClass, groupID, cronJobExpression);

        try {
            scheduler.scheduleJob(jobDetail, trigger);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    @PreDestroy
    public void preDestroy(){
        try {
            scheduler.shutdown();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }
}
