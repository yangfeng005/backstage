package com.backstage.system.log;

import com.alibaba.fastjson.JSONObject;
import com.backstage.common.utils.http.IPUtil;
import com.backstage.common.utils.lang.DateTimeUtil;
import com.backstage.system.entity.customized.UserAO;
import com.backstage.system.auth.AuthUtil;
import com.backstage.system.entity.customized.SysLogAO;
import com.backstage.system.service.ISysLogService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * @author yangfeng
 * @date 2019年9月10日
 */
@Aspect
@Component
public class LogOperationAspect {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    private ISysLogService sysLogService;

    public LogOperationAspect() {
    }

    /**
     * 定义切入点对象表达式 execution
     * 注解参数 logOperation
     */
    private static final String pointCutExpression = "execution(@LogOperation * *(..)) && @annotation(logOperation)";

    /**
     * 切入点声明
     *
     * @param logOperation
     */
    @Pointcut(pointCutExpression)
    public void handlePointCut(LogOperation logOperation) {
    }

    /**
     * Before 增强。对handlePointCut进行before增强
     *
     * @param joinPoint    被通知的对象的对象、参数。。。
     * @param logOperation 注解参数提取action Name
     */
    @Before(value = "handlePointCut(logOperation)")
    public void handleBefore(JoinPoint joinPoint, LogOperation logOperation) {
        try {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            if (request != null) {
                SysLogAO log = new SysLogAO();
                log.setAction(logOperation.action());
                log.setIp(IPUtil.getClientIp(request));
                log.setOperateContent(JSONObject.toJSONString(request.getParameterMap()));
                log.setOperateObject(joinPoint.getStaticPart().toString());
                log.setOperateTime(DateTimeUtil.formatDateTime(new Date()));
                UserAO user = AuthUtil.getCurrentUser();
                log.setUser(user == null ? null : user.getUserName());
                sysLogService.insert(log);
            }
        } catch (Exception e) {
            logger.error("LogOperationAspect before... ", e);
        }
    }


}
