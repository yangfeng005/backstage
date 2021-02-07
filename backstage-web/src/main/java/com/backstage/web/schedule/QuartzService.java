package com.backstage.web.schedule;

import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * 定时任务服务
 *
 * @author yangfeng
 * @date 2018-07-03
 */
@Service
public class QuartzService {

    private static final String JOB_GROUP = "event_job_group";
    private static final String TRIGGER_GROUP = "event_trigger_group";

    @Autowired
    private Scheduler scheduler;

    /**
     * 创建定时任务,通过注入Scheduler对任务进行操作
     *
     * @param jobDetailName
     * @param cronExpression
     * @param jobClass
     * @throws SchedulerException
     */
    public void createScheduleJob(String jobDetailName, String cronExpression, Class<? extends Job> jobClass, String param) throws SchedulerException {
        //先把已经有的删除了
        deleteScheduleJob(jobDetailName, JOB_GROUP);
        JobDetail jobDetail = JobBuilder.newJob(jobClass)
                .withIdentity("task_" + jobDetailName, JOB_GROUP).storeDurably().requestRecovery().build();
        //参数传递
        if (!StringUtils.isEmpty(param)) {
            jobDetail.getJobDataMap().put("task_" + jobDetail.getKey(), param);
        }
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cronExpression);
        Trigger trigger = TriggerBuilder.newTrigger().withIdentity("task_" + jobDetailName, TRIGGER_GROUP).withSchedule(scheduleBuilder).build();
        scheduler.scheduleJob(jobDetail, trigger);
    }

    /**
     * 删除定时任务
     *
     * @param jobDetailName
     * @param jobGroup
     * @return
     * @throws SchedulerException
     */
    public boolean deleteScheduleJob(String jobDetailName, String jobGroup) throws SchedulerException {
        JobKey jobKey = JobKey.jobKey("task_" + jobDetailName, jobGroup);
        return scheduler.deleteJob(jobKey);
    }
}