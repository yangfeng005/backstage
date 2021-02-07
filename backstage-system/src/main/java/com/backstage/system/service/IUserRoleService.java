package com.backstage.system.service;

import com.backstage.core.result.ServiceResult;
import com.backstage.core.service.IBaseAOService;
import com.backstage.system.entity.customized.UserRoleAO;
import com.backstage.system.entity.gen.UserRoleCriteria;

import java.util.List;

public interface IUserRoleService extends IBaseAOService<UserRoleAO, UserRoleCriteria> {

    /**
     * 根据用户id获取用户角色
     * @param userId
     * @return
     */
    List<UserRoleAO> getUserRoles(String userId);

    /**
     * 删除用户关联的角色
     * @param userId
     * @return
     */
    ServiceResult<Boolean> deleteByUserId(String userId);
}
