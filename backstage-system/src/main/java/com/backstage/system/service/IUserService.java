package com.backstage.system.service;

import com.backstage.core.result.ServiceResult;
import com.backstage.core.service.IBaseAOService;
import com.backstage.system.dto.request.UserRequest;
import com.backstage.system.entity.customized.UserAO;
import com.backstage.system.entity.gen.UserCriteria;

import java.util.List;

public interface IUserService extends IBaseAOService<UserAO, UserCriteria> {

    /**
     * 根据用户名获取用户信息
     *
     * @param userName
     * @return
     */
    UserAO getUserByName(String userName);

    /**
     * 根据用户名和密码获取用户信息
     *
     * @param userName
     * @param pwd
     * @return
     */
    UserAO getUserByNameAndPwd(String userName, String pwd);


    /**
     * 查询用户
     *
     * @return
     */
    ServiceResult<List<UserAO>> list(UserRequest request);


    /**
     * 批量更新用户状态
     *
     * @param userIds
     * @param status
     * @return
     */
    ServiceResult<Boolean> updateStatus(String[] userIds, Integer status);

    /**
     * 保存用户
     *
     * @param user
     * @return
     */
    ServiceResult<Boolean> save(UserAO user);


    /**
     * 保存用户角色
     *
     * @param userId
     * @param roleIds
     * @return
     */
    ServiceResult<Boolean> saveUserRole(String userId, String[] roleIds);


    /**
     * 根据角色id查看角色对应的用户
     *
     * @param roleId
     * @return
     */
    List<UserAO> getUsersByRoleId(String roleId);

    /**
     * 修改密码
     *
     * @param userName
     * @param originPassword
     * @param newPassword
     * @return
     */
    ServiceResult<Integer> updatePwd(String userName, String originPassword, String newPassword);

    /**
     * 根据id查询用户
     *
     * @param id
     * @return
     */
    ServiceResult<UserAO> getUserById(String id);


}
