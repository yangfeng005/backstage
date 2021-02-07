package com.backstage.system.timecost;

import com.alibaba.fastjson.JSONObject;
import com.backstage.common.utils.http.IPUtil;
import com.backstage.common.utils.lang.DateTimeUtil;
import com.backstage.common.utils.math.MathUtil;
import com.backstage.system.auth.AuthUtil;
import com.backstage.system.entity.customized.TimeCostAO;
import com.backstage.system.service.ITimeCostService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Date;

@Aspect
@Component
public class TimeCostAspect {

    @Resource
    private ITimeCostService timeCostService;

    private static Logger logger = LoggerFactory.getLogger(TimeCostAspect.class);

    private static final String pointCutExpression = "execution(@TimeCost * *(..)) && @annotation(timeCost)";

    @Pointcut(pointCutExpression)
    public void handlePointCut(TimeCost timeCost) {

    }

    @Around("handlePointCut(timeCost)")
    public Object watchPerformance(ProceedingJoinPoint joinPoint, TimeCost timeCost) throws Throwable {
        // 定义返回对象、得到方法需要的参数
        Object obj = null;
        Object[] args = joinPoint.getArgs();
        long startTime = System.currentTimeMillis();
        //执行方法
        obj = joinPoint.proceed(args);
        // 获取执行的方法名
        long endTime = System.currentTimeMillis();
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        if (request != null) {
            TimeCostAO timeCostAO = new TimeCostAO();
            timeCostAO.setActionName(timeCost.name());
            timeCostAO.setIp(IPUtil.getClientIp(request));
            timeCostAO.setOperateContent(JSONObject.toJSONString(request.getParameterMap()));
            timeCostAO.setOperateObject(joinPoint.getStaticPart().toString());
            timeCostAO.setStartTime(String.valueOf(startTime));
            timeCostAO.setEndTime(String.valueOf(endTime));
            timeCostAO.setTimeCost("耗时：" + MathUtil
                    .divideAndRound(new BigDecimal(endTime - startTime), new BigDecimal(1000)) + "秒");
            timeCostAO.setUser(AuthUtil.getCurrentUser().getUserName());
            timeCostAO.setCreateTime(DateTimeUtil.formatDateTime(new Date()));
            timeCostService.insert(timeCostAO);
        }
        return obj;
    }
}