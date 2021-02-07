package com.backstage.system.controller;

import com.backstage.system.log.LogOperation;
import com.backstage.system.service.IResourceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 资源管理
 *
 * @author yangfeng
 * @date 2019-09-11 16:02
 */
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
    @RequestMapping(value = "/getResourceTreeNode", method = {RequestMethod.GET, RequestMethod.POST})
    @LogOperation(action = "获取资源树结构")
    public Object getResourceTreeNode() {
        return resourceService.getResourceTreeNode();
    }


    /**
     * 获取资源.
     *
     * @return
     */
    @RequestMapping(value = "/getResource", method = {RequestMethod.GET, RequestMethod.POST})
    @LogOperation(action = "获取资源")
    public Object getResource(@RequestParam String userName) {
        return resourceService.getResource(userName);
    }
}