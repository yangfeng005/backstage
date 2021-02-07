package com.backstage.system.controller;

import com.backstage.system.dto.request.UserRequest;
import com.backstage.system.entity.customized.UserAO;
import com.backstage.system.log.LogOperation;
import com.backstage.system.service.IUserService;
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
 * 用户管理
 *
 * @author yangfeng
 * @date 2019-09-11 16:02
 */
@RestController
@RequestMapping(value = "user")
public class UserController {

    private static Logger LOG = LoggerFactory.getLogger(UserController.class);

    @Resource
    private IUserService userService;


    @RequestMapping(value = "/list", method = {RequestMethod.GET, RequestMethod.POST})
    @LogOperation(action = "查询用户列表")
    @RequiresPermissions(value = {"userManage:view", "userManage:manage"}, logical = Logical.OR)
    public Object list(UserRequest userRequest) {
        return userService.list(userRequest);
    }

    /**
     * 启用/禁用-更新用户状态.
     *
     * @param userIds 用户主键id数组.
     * @param status  状态值.
     * @return
     */
    @RequestMapping(value = "/updateStatus", method = {RequestMethod.GET, RequestMethod.POST})
    @RequiresPermissions("userManage:manage")
    @LogOperation(action = "启用/禁用-更新用户状态")
    public Object updateStatus(String[] userIds, Integer status) {
        return userService.updateStatus(userIds, status);
    }

    /**
     * 保存数据（新增/修改用户）.
     *
     * @param user 前端传入的数据对象.
     * @return
     */
    @RequestMapping(value = "/save", method = {RequestMethod.GET, RequestMethod.POST})
    @RequiresPermissions("userManage:manage")
    @LogOperation(action = "保存数据（新增/修改用户）")
    public Object save(UserAO user) {
        return userService.save(user);
    }


    /**
     * 保存用户角色.
     *
     * @param userId  用户id.
     * @param roleIds 角色id数组
     * @return
     */
    @RequestMapping(value = "/saveUserRole", method = {RequestMethod.GET, RequestMethod.POST})
    @RequiresPermissions("userManage:manage")
    @LogOperation(action = "保存用户角色（新增/修改）")
    public Object saveUserRole(@RequestParam String userId, String[] roleIds) {
        return userService.saveUserRole(userId, roleIds);
    }

    /**
     * 修改账号密码
     *
     * @param userName
     * @param originPassword
     * @param newPassword
     * @return
     */
    @RequestMapping(value = "/updatePwd", method = {RequestMethod.GET, RequestMethod.POST})
    @RequiresPermissions("userManage:manage")
    @LogOperation(action = "修改账号密码")
    public Object updatePwd(@RequestParam String userName, @RequestParam String originPassword,
                            @RequestParam String newPassword) {
        return userService.updatePwd(userName, originPassword, newPassword);
    }

    /**
     * 根据id查询用户
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/getUserById", method = {RequestMethod.GET, RequestMethod.POST})
    @RequiresPermissions(value = {"userManage:view", "userManage:manage"}, logical = Logical.OR)
    @LogOperation(action = "根据id查询用户")
    public Object getUserById(@RequestParam String id) {
        return userService.getUserById(id);
    }

}