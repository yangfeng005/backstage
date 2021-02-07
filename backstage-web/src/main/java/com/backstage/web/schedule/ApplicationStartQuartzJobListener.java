package com.backstage.web.schedule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;

/**
 * 监听器，启动定时任务
 *
 * @author yangfeng
 * @date 2019-03-12
 */
@Configuration
public class ApplicationStartQuartzJobListener implements ApplicationListener<ContextRefreshedEvent> {
    private static Logger LOG = LoggerFactory.getLogger(ApplicationStartQuartzJobListener.class);

    @Autowired
    private QuartzService quartzService;

    /**
     * 初始启动quartz
     */
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        try {

        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
    }
}
