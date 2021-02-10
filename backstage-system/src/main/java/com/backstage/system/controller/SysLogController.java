package com.backstage.system.controller;

import com.backstage.system.dto.request.SysLogRequest;
import com.backstage.system.service.ISysLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Created by yf on 2020/04/10.
 * 操作日志 controller
 */
@Api(tags = "操作日志")
@RestController
@RequestMapping("/sysLog")
public class SysLogController {

    private static Logger LOG = LoggerFactory.getLogger(SysLogController.class);

    @Resource
    private ISysLogService sysLogService;


    /**
     * 查询
     *
     * @return
     */
    @ApiOperation(value = "查询操作日志", notes = "查询操作日志")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userName", value = "用户名", dataType = "String", paramType = "query", required = false),
            @ApiImplicitParam(name = "operateTime", value = "操作日期", dataType = "String", paramType = "query", required = false),
    })
    @PostMapping("/list")
    @RequiresPermissions(value = {"sysLog:view", "sysLog:manage"}, logical = Logical.OR)
    public Object list(SysLogRequest request) {
        return sysLogService.list(request);
    }

}
