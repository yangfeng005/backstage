package com.backstage.system.controller;

import com.backstage.system.dto.request.AgencyRequest;
import com.backstage.system.entity.customized.AgencyAO;
import com.backstage.system.log.LogOperation;
import com.backstage.system.service.IAgencyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
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
@Api(tags = "组织机构管理")
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
    @ApiOperation(value = "查询机构树形结构数据", notes = "查询机构树形结构数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "机构名称", dataType = "String", paramType = "query", required = false),
            @ApiImplicitParam(name = "code", value = "机构编码", dataType = "String", paramType = "query", required = false),
    })
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
    /**
     * 保存机构
     *
     * @param agency 前端传入的数据对象.
     * @return
     */
    @ApiOperation(value = "保存机构", notes = "保存机构")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id,编辑时不能为空", dataType = "String", paramType = "query", required = false),
            @ApiImplicitParam(name = "code", value = "机构编码", dataType = "String", paramType = "query", required = true),
            @ApiImplicitParam(name = "name", value = "机构名称", dataType = "String", paramType = "query", required = true),
            @ApiImplicitParam(name = "parentCode", value = "父级机构编码", dataType = "String", paramType = "query", required = false),
            @ApiImplicitParam(name = "rank", value = "排序", dataType = "Integer", paramType = "query", required = true),
            @ApiImplicitParam(name = "email", value = "电子邮件", dataType = "String", paramType = "query", required = false),
            @ApiImplicitParam(name = "zipCode", value = "邮政编码", dataType = "String", paramType = "query", required = false),
            @ApiImplicitParam(name = "phone", value = "办公电话", dataType = "String", paramType = "query", required = false),
            @ApiImplicitParam(name = "address", value = "机构地址", dataType = "String", paramType = "query", required = false),
            @ApiImplicitParam(name = "description", value = "备注", dataType = "String", paramType = "query", required = false),
            @ApiImplicitParam(name = "status", value = "状态 1：启用 2：禁用", dataType = "String", paramType = "query", required = true),
    })
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
    @ApiOperation(value = "删除机构", notes = "删除机构")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "agencyId", value = "机构id", dataType = "String", paramType = "query", required = true),
    })
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
    @ApiOperation(value = "编辑时选择上级机构", notes = "编辑时选择上级机构")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "agencyId", value = "机构id", dataType = "String", paramType = "query", required = true),
    })
    @GetMapping("/listOthers")
    @RequiresPermissions(value = {"agencyManage:view", "agencyManage:manage"}, logical = Logical.OR)
    @LogOperation(action = "编辑时选择上级机构排除自身")
    public Object listOthers(@RequestParam String agencyId) {
        return agencyService.listOthers(agencyId);
    }

}
