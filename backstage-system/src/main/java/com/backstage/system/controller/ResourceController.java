package com.backstage.system.controller;

import com.backstage.system.log.LogOperation;
import com.backstage.system.service.IResourceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 资源管理
 *
 * @author yangfeng
 * @date 2019-09-11 16:02
 */
@Api(tags = "资源管理")
@RestController
@RequestMapping(value = "resource")
public class ResourceController {

    private static Logger LOG = LoggerFactory.getLogger(ResourceController.class);

    @Resource
    private IResourceService resourceService;

    /**
     * 获取资源树结构.
     *
     * @return
     */
    @ApiOperation(value = "获取资源树结构", notes = "获取资源树结构")
    @ApiImplicitParams({})
    @PostMapping("/getResourceTreeNode")
    @LogOperation(action = "获取资源树结构")
    public Object getResourceTreeNode() {
        return resourceService.getResourceTreeNode();
    }


    /**
     * 根据用户名获取资源.
     *
     * @return
     */
    @ApiOperation(value = "根据用户名获取资源", notes = "根据用户名获取资源")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userName", value = "用户名", dataType = "String", paramType = "query", required = true),
    })
    @PostMapping("/getResource")
    @LogOperation(action = "根据用户名获取资源")
    public Object getResource(@RequestParam String userName) {
        return resourceService.getResource(userName);
    }
}