package com.backstage.web.service.impl;

/**
 * @author yangfeng
 * @date 2019/8/29
 * @description 定时任务服务
 */

import com.backstage.web.service.IJobService;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * @author yangfeng
 * @date 2018/08/28
 */
@Service
public class JobService implements IJobService {
    private static Logger LOG = LoggerFactory.getLogger(JobService.class);

    @Autowired
    private SchedulerFactoryBean schedulerFactoryBean;


    /**
     * 创建任务
     *
     * @param jobName  任务名
     * @param jobClass 任务
     * @param jobGroup
     */
    @Override
    public void addCronJob(String cron, String jobName, String jobGroup, Class jobClass, String param) throws SchedulerException {
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        //构建job信息
        JobDetail jobDetail = JobBuilder.newJob(jobClass).withIdentity(jobName, jobGroup).storeDurably().requestRecovery().build();
        //参数传递
        if (!StringUtils.isEmpty(param)) {
            jobDetail.getJobDataMap().put("task_" + jobDetail.getKey(), param);
        }
        //表达式调度构建器(即任务执行的时间,每5秒执行一次)
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cron);

        //按新的cronExpression表达式构建一个新的trigger
        CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(jobName + "_trigger", jobGroup + "_trigger")
                .withSchedule(scheduleBuilder).build();
        scheduler.scheduleJob(jobDetail, trigger);
    }

    /**
     * 更新任务
     *
     * @param cron     定时任务表达式
     * @param jobName
     * @param jobGroup
     */
    @Override
    public void updateCronJob(String cron, String jobName, String jobGroup) throws SchedulerException {
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        TriggerKey triggerKey = TriggerKey.triggerKey(jobName + "_trigger", jobGroup + "_trigger");
        CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
        if (trigger == null) {
            return;
        }
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cron);
        trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();
        scheduler.rescheduleJob(triggerKey, trigger);
    }

    @Override
    public void pauseJob(String jobName, String jobGroup) throws SchedulerException {
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        TriggerKey triggerKey = TriggerKey.triggerKey(jobName + "_trigger", jobGroup + "_trigger");
        scheduler.pauseTrigger(triggerKey);
    }

    /**
     * 恢复任务
     *
     * @param jobName
     * @param jobGroup
     */
    @Override
    public void resumeJob(String jobName, String jobGroup) throws SchedulerException {
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        TriggerKey triggerKey = TriggerKey.triggerKey(jobName + "_trigger", jobGroup + "_trigger");
        scheduler.resumeTrigger(triggerKey);
    }

    @Override
    public void deleteJob(String jobName, String jobGroup) throws SchedulerException {
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        JobKey jobKey = JobKey.jobKey(jobName, jobGroup);
        scheduler.deleteJob(jobKey);
    }

    @Override
    public boolean isScheduling(String jobName, String jobGroup) throws SchedulerException {
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        TriggerKey triggerKey = TriggerKey.triggerKey(jobName + "_trigger", jobGroup + "_trigger");
        CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
        return trigger != null;
    }
}