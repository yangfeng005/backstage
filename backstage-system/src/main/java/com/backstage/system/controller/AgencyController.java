package com.backstage.system.controller;

import com.backstage.system.dto.request.AgencyRequest;
import com.backstage.system.log.LogOperation;
import com.backstage.system.entity.customized.AgencyAO;
import com.backstage.system.service.IAgencyService;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * Created by yf on 2019/11/13.
 * 机构 controller
 */
@RestController
@RequestMapping("/agency")
public class AgencyController {

    private static Logger LOG = LoggerFactory.getLogger(AgencyController.class);

    @Resource
    private IAgencyService agencyService;


    /**
     * 查询机构树形结构数据
     *
     * @return
     */
    @PostMapping("/list")
    @RequiresPermissions(value = {"agencyManage:view", "agencyManage:manage"}, logical = Logical.OR)
    @LogOperation(action = "查询机构树形结构数据")
    public Object list(AgencyRequest request) {
        return agencyService.list(request);
    }


    /**
     * 保存机构
     *
     * @param agency 前端传入的数据对象.
     * @return
     */
    @PostMapping("/save")
    @RequiresPermissions("agencyManage:manage")
    @LogOperation(action = "保存机构")
    public Object save(AgencyAO agency) {
        return agencyService.save(agency);
    }


    /**
     * 删除机构
     *
     * @param agencyId 主键id .
     * @return
     */
    @GetMapping("/delete")
    @RequiresPermissions("agencyManage:manage")
    @LogOperation(action = "删除机构")
    public Object delete(@RequestParam String agencyId) {
        return agencyService.delete(agencyId);
    }


    /**
     * 编辑时选择上级机构要排除自身
     *
     * @param agencyId 主键id .
     * @return
     */
    @GetMapping("/listOthers")
    @RequiresPermissions(value = {"agencyManage:view", "agencyManage:manage"}, logical = Logical.OR)
    @LogOperation(action = "编辑时选择上级机构排除自身")
    public Object listOthers(@RequestParam String agencyId) {
        return agencyService.listOthers(agencyId);
    }

}
