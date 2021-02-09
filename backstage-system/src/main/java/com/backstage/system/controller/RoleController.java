package com.backstage.system.controller;

import com.backstage.system.dto.request.RoleRequest;
import com.backstage.system.entity.customized.RoleAO;
import com.backstage.system.log.LogOperation;
import com.backstage.system.service.IRolePrivilegeService;
import com.backstage.system.service.IRoleService;
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
 * 角色管理
 *
 * @author yangfeng
 * @date 2019-09-11 16:02
 */
@Api(tags = "角色管理")
@RestController
@RequestMapping(value = "role")
public class RoleController {

    private static Logger LOG = LoggerFactory.getLogger(RoleController.class);

    @Resource
    private IRoleService roleService;

    @Resource
    private IRolePrivilegeService rolePrivilegeService;


    /**
     * 获取角色列表
     *
     * @return
     */
    @ApiOperation(value = "获取角色列表", notes = "获取角色列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNo", value = "当前页数（从1开始）", dataType = "Integer", paramType = "query", required = true),
            @ApiImplicitParam(name = "pageSize", value = "分页记录数", dataType = "Integer", paramType = "query", required = true),
            @ApiImplicitParam(name = "status", value = "状态: 1启用 2禁用", dataType = "Integer", paramType = "query", required = false),
            @ApiImplicitParam(name = "code", value = "角色编码", dataType = "String", paramType = "query", required = false),
            @ApiImplicitParam(name = "name", value = "角色名称", dataType = "String", paramType = "query", required = false),
            @ApiImplicitParam(name = "agencyCode", value = "所属机构编码", dataType = "String", paramType = "query", required = false),
            @ApiImplicitParam(name = "agencyName", value = "所属机构名称", dataType = "String", paramType = "query", required = false),
    })
    @PostMapping("/list")
    @RequiresPermissions(value = {"roleManage:view", "roleManage:manage"}, logical = Logical.OR)
    @LogOperation(action = "获取角色列表")
    public Object list(RoleRequest request) {
        return roleService.list(request);
    }


    /**
     * 不分页获取角色列表
     *
     * @return
     */
    @ApiOperation(value = "不分页获取角色列表", notes = "不分页获取角色列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "status", value = "状态: 1启用 2禁用", dataType = "Integer", paramType = "query", required = false),
            @ApiImplicitParam(name = "code", value = "角色编码", dataType = "String", paramType = "query", required = false),
            @ApiImplicitParam(name = "name", value = "角色名称", dataType = "String", paramType = "query", required = false),
            @ApiImplicitParam(name = "agencyCode", value = "所属机构编码", dataType = "String", paramType = "query", required = false),
            @ApiImplicitParam(name = "agencyName", value = "所属机构名称", dataType = "String", paramType = "query", required = false),
    })
    @PostMapping("/listNoPage")
    @RequiresPermissions(value = {"roleManage:view", "roleManage:manage"}, logical = Logical.OR)
    @LogOperation(action = "不分页获取角色列表")
    public Object listNoPage(RoleRequest request) {
        return roleService.listNoPage(request);
    }

    /**
     * 保存角色.
     *
     * @param role
     * @return
     */
    @ApiOperation(value = "保存角色", notes = "保存角色")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "名称", dataType = "String", paramType = "query", required = true),
            @ApiImplicitParam(name = "code", value = "编码", dataType = "String", paramType = "query", required = true),
            @ApiImplicitParam(name = "rank", value = "排序", dataType = "Integer", paramType = "query", required = false),
            @ApiImplicitParam(name = "description", value = "备注", dataType = "String", paramType = "query", required = false),
            @ApiImplicitParam(name = "agencyCode", value = "所属机构编码", dataType = "String", paramType = "query", required = true),
    })
    @PostMapping("/save")
    @RequiresPermissions("roleManage:manage")
    @LogOperation(action = "保存（新增、修改）角色")
    public Object save(RoleAO role) {
        return roleService.save(role);
    }

    /**
     * 保存角色权限.
     *
     * @param privilegeIds
     * @param roleId
     * @return
     */
    @ApiOperation(value = "保存角色权限", notes = "保存角色权限")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleId", value = "角色id", dataType = "String", paramType = "query", required = true),
            @ApiImplicitParam(name = "privilegeIds", value = "权限id数组", dataType = "String[]", paramType = "query", required = true),
    })
    @PostMapping("/savePrivilege")
    @RequiresPermissions("roleManage:manage")
    @LogOperation(action = "保存角色权限")
    public Object savePrivilege(@RequestParam String roleId, String[] privilegeIds) {
        return roleService.savePrivilege(roleId, privilegeIds);
    }


    /**
     * 获取角色权限.
     *
     * @param roleId
     * @return
     */
    @ApiOperation(value = "获取角色权限", notes = "获取角色权限")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleId", value = "角色id", dataType = "String", paramType = "query", required = true),
    })
    @PostMapping("/getRolePrivilege")
    @RequiresPermissions(value = {"roleManage:view", "roleManage:manage"}, logical = Logical.OR)
    @LogOperation(action = "获取角色权限")
    public Object getRolePrivilege(@RequestParam String roleId) {
        return rolePrivilegeService.getRolePrivilege(roleId);
    }

    /**
     * 删除角色.
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "删除角色", notes = "删除角色")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "角色id", dataType = "String", paramType = "query", required = true),
    })
    @GetMapping("/deleteRole")
    @RequiresPermissions("roleManage:manage")
    @LogOperation(action = "删除角色")
    public Object deleteRole(@RequestParam String id) {
        return roleService.deleteRole(id);
    }

    /**
     * 根据id查询角色
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "根据id查询角色", notes = "根据id查询角色")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "角色id", dataType = "String", paramType = "query", required = true),
    })
    @GetMapping("/getRoleById")
    @RequiresPermissions(value = {"roleManage:view", "roleManage:manage"}, logical = Logical.OR)
    @LogOperation(action = "根据id查询角色")
    public Object getRoleById(@RequestParam String id) {
        return roleService.selectByPrimaryKey(id);
    }

    /**
     * 根据用户id查询角色
     *
     * @param userId
     * @return
     */
    @ApiOperation(value = "根据用户id查询角色", notes = "根据用户id查询角色")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户id", dataType = "String", paramType = "query", required = true),
    })
    @PostMapping("/getRoleByUserId")
    @RequiresPermissions(value = {"roleManage:view", "roleManage:manage"}, logical = Logical.OR)
    @LogOperation(action = "根据用户id查询角色")
    public Object getRoleByUserId(@RequestParam String userId) {
        return roleService.getRolesByUserId(userId);
    }

}