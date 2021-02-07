package com.backstage.system.service;


import com.backstage.core.result.ServiceResult;
import com.backstage.core.service.IBaseAOService;
import com.backstage.system.dto.request.RoleRequest;
import com.backstage.system.entity.customized.RoleAO;
import com.backstage.system.entity.gen.RoleCriteria;

import java.util.List;

public interface IRoleService extends IBaseAOService<RoleAO, RoleCriteria> {

    /**
     * 根据用户id查询角色
     *
     * @param userId
     * @return
     */
    ServiceResult<List<RoleAO>> getRolesByUserId(String userId);

    /**
     * 根据用户名查询角色
     *
     * @param userName
     * @return
     */
    ServiceResult<List<RoleAO>> getRolesByUserName(String userName);

    /**
     * 分页查询角色
     */
    ServiceResult<List<RoleAO>> list(RoleRequest request);


    /**
     * 不分页查询角色
     */
    ServiceResult<List<RoleAO>> listNoPage(RoleRequest request);

    /**
     * 保存角色
     *
     * @param role
     * @return
     */
    ServiceResult<Boolean> save(RoleAO role);


    /**
     * 保存权限
     *
     * @param roleId
     * @param privilegeIds
     * @return
     */
    ServiceResult<Boolean> savePrivilege(String roleId, String[] privilegeIds);

    /**
     * 删除角色
     *
     * @param roleId
     * @return
     */
    ServiceResult<Boolean> deleteRole(String roleId);

    /**
     * 获取数据权限配置
     *
     * @param userName
     * @return
     */
    Integer getDataScopeByUserName(String userName);


    /**
     * 根据用户id查询角色id集合
     *
     * @param userId
     * @return
     */
    ServiceResult<List<String>> getRoleIdsByUserId(String userId);

}
