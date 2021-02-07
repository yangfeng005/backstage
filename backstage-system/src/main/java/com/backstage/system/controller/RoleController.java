package com.backstage.system.controller;

import com.backstage.system.dto.request.RoleRequest;
import com.backstage.system.entity.customized.RoleAO;
import com.backstage.system.log.LogOperation;
import com.backstage.system.service.IRolePrivilegeService;
import com.backstage.system.service.IRoleService;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 角色管理
 *
 * @author yangfeng
 * @date 2019-09-11 16:02
 */
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
    @RequestMapping(value = "/list", method = {RequestMethod.GET, RequestMethod.POST})
    @RequiresPermissions(value = {"roleManage:view", "roleManage:manage"}, logical = Logical.OR)
    @LogOperation(action = "获取角色列表")
    public Object list(RoleRequest request) {
        return roleService.listRoles(request);
    }

    /**
     * 保存角色.
     *
     * @param role
     * @return
     */
    @RequestMapping(value = "/save", method = {RequestMethod.GET, RequestMethod.POST})
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
    @RequestMapping(value = "/savePrivilege", method = {RequestMethod.GET, RequestMethod.POST})
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
    @RequestMapping(value = "/getRolePrivilege", method = {RequestMethod.GET, RequestMethod.POST})
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
    @RequestMapping(value = "/deleteRole", method = {RequestMethod.GET, RequestMethod.POST})
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
    @RequestMapping(value = "/getRoleById", method = {RequestMethod.GET, RequestMethod.POST})
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
    @RequestMapping(value = "/getRoleByUserId", method = {RequestMethod.GET, RequestMethod.POST})
    @RequiresPermissions(value = {"roleManage:view", "roleManage:manage"}, logical = Logical.OR)
    @LogOperation(action = "根据用户id查询角色")
    public Object getRoleByUserId(@RequestParam String userId) {
        return roleService.getRolesByUserId(userId);
    }

}