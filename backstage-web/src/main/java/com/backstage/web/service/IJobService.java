package com.backstage.web.service;

import org.quartz.SchedulerException;

/**
 * @author yangfeng
 * @date 2019/8/29
 * @description
 */
public interface IJobService {
    /**
     * 添加一个定时任务
     *
     * @param cron     定时任务表达式
     * @param jobName
     * @param jobGroup
     */
    void addCronJob(String cron, String jobName, String jobGroup, Class clazz, String param) throws SchedulerException;


    /**
     * 更新定时任务
     *
     * @param cron     定时任务表达式
     * @param jobName
     * @param jobGroup
     */
    void updateCronJob(String cron, String jobName, String jobGroup) throws SchedulerException;


    /**
     * 暂停任务
     *
     * @param jobName
     * @param jobGroup
     */
    void pauseJob(String jobName, String jobGroup) throws SchedulerException;

    /**
     * 恢复任务
     *
     * @param triggerName
     * @param triggerGroup
     */
    void resumeJob(String triggerName, String triggerGroup) throws SchedulerException;

    /**
     * 删除job
     *
     * @param jobName
     * @param jobGroup
     */
    void deleteJob(String jobName, String jobGroup) throws SchedulerException;

    /**
     * 是否正在运行中
     *
     * @param jobName
     * @param jobGroup
     * @return
     */
    boolean isScheduling(String jobName, String jobGroup) throws SchedulerException;
}