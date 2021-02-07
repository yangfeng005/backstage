package com.backstage.system.controller;

import com.backstage.system.dto.request.UserRequest;
import com.backstage.system.entity.customized.UserAO;
import com.backstage.system.log.LogOperation;
import com.backstage.system.service.IUserService;
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
 * 用户管理
 *
 * @author yangfeng
 * @date 2019-09-11 16:02
 */
@Api(tags = "用户管理")
@RestController
@RequestMapping(value = "user")
public class UserController {

    private static Logger LOG = LoggerFactory.getLogger(UserController.class);

    @Resource
    private IUserService userService;


    @ApiOperation(value = "查询用户数据", notes = "查询用户数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNo", value = "当前页数（从1开始）", dataType = "Integer", paramType = "query", required = true),
            @ApiImplicitParam(name = "pageSize", value = "分页记录数", dataType = "Integer", paramType = "query", required = true),
            @ApiImplicitParam(name = "userName", value = "用户名", dataType = "String", paramType = "query", required = false),
            @ApiImplicitParam(name = "trueName", value = "真实姓名", dataType = "String", paramType = "query", required = false),
            @ApiImplicitParam(name = "status", value = "状态 0 删除 1启用 2禁用", dataType = "Integer", paramType = "query", required = false),
            @ApiImplicitParam(name = "orderBy", value = "排序", dataType = "String", paramType = "query", required = false),
            @ApiImplicitParam(name = "agencyCode", value = "所属机构编码", dataType = "String", paramType = "query", required = false),
            @ApiImplicitParam(name = "agencyName", value = "所属机构名称", dataType = "String", paramType = "query", required = false),
    })
    @PostMapping("/list")
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
    @ApiOperation(value = "更新用户状态", notes = "更新用户状态")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userIds", value = "用户主键id数组", dataType = "String[]", paramType = "query", required = true),
            @ApiImplicitParam(name = "status", value = "状态: 0 删除 1启用 2禁用", dataType = "Integer", paramType = "query", required = true),
    })
    @PostMapping("/updateStatus")
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
    @ApiOperation(value = "新增/修改用户", notes = "新增/修改用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户id(修改时需要传入)", dataType = "String", paramType = "query", required = false),
            @ApiImplicitParam(name = "agencyCode", value = "所属机构编码", dataType = "String", paramType = "query", required = false),
            @ApiImplicitParam(name = "userName", value = "用户名", dataType = "String", paramType = "query", required = true),
            @ApiImplicitParam(name = "nickName", value = "用户昵称", dataType = "String", paramType = "query", required = false),
            @ApiImplicitParam(name = "trueName", value = "真实姓名", dataType = "String", paramType = "query", required = false),
            @ApiImplicitParam(name = "gender", value = "性别 1:男 2:女", dataType = "String", paramType = "query", required = false),
            @ApiImplicitParam(name = "age", value = "年龄", dataType = "String", paramType = "query", required = false),
            @ApiImplicitParam(name = "email", value = "邮件", dataType = "String", paramType = "query", required = false),
            @ApiImplicitParam(name = "mobile", value = "电话", dataType = "String", paramType = "query", required = false),
            @ApiImplicitParam(name = "status", value = "状态 0 删除 1启用 2禁用", dataType = "Integer", paramType = "query", required = true),
    })
    @PostMapping("/save")
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
    @ApiOperation(value = "保存用户角色", notes = "保存用户角色")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户id", dataType = "String", paramType = "query", required = true),
            @ApiImplicitParam(name = "roleIds", value = "角色id数组", dataType = "String", paramType = "query", required = true),
    })
    @PostMapping("/saveUserRole")
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
    @ApiOperation(value = "修改账号密码", notes = "修改账号密码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userName", value = "用户名", dataType = "String", paramType = "query", required = true),
            @ApiImplicitParam(name = "originPassword", value = "原密码", dataType = "String", paramType = "query", required = true),
            @ApiImplicitParam(name = "newPassword", value = "新密码", dataType = "String", paramType = "query", required = true),
    })
    @PostMapping("/updatePwd")
    @RequiresPermissions("userManage:manage")
    @LogOperation(action = "修改账号密码")
    public Object updatePwd(@RequestParam String userName, @RequestParam String originPassword,
                            @RequestParam String newPassword) {
        return userService.updatePwd(userName, originPassword, newPassword);
    }

    /**
     * 重置密码
     *
     * @param userName
     * @return
     */
    @ApiOperation(value = "重置密码", notes = "重置密码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userName", value = "用户名", dataType = "String", paramType = "query", required = true),
    })
    @GetMapping("/resetPwd")
    @RequiresPermissions("userManage:manage")
    @LogOperation(action = "重置密码")
    public Object resetPwd(@RequestParam String userName) {
        return userService.resetPwd(userName);
    }

    /**
     * 根据id查询用户
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "根据id查询用户", notes = "根据id查询用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户id", dataType = "String", paramType = "query", required = true),
    })
    @GetMapping("/getUserById")
    @RequiresPermissions(value = {"userManage:view", "userManage:manage"}, logical = Logical.OR)
    @LogOperation(action = "根据id查询用户")
    public Object getUserById(@RequestParam String id) {
        return userService.getUserById(id);
    }

}