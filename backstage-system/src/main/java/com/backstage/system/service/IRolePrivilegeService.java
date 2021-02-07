package com.backstage.system.service;

import com.backstage.core.result.ServiceResult;
import com.backstage.core.service.IBaseAOService;
import com.backstage.system.entity.customized.RolePrivilegeAO;
import com.backstage.system.entity.gen.RolePrivilegeCriteria;

import java.util.List;

public interface IRolePrivilegeService extends IBaseAOService<RolePrivilegeAO, RolePrivilegeCriteria> {

    /**
     * 根据角色id删除权限
     * @param roleId
     * @return
     */
    ServiceResult<Boolean> deleteByRoleId(String roleId);

    /**
     * 获取角色拥有的权限
     * @param roleId
     * @return
     */
    ServiceResult<List<RolePrivilegeAO>> getRolePrivilege(String roleId);

}
