package com.backstage.system.service.impl;

import com.backstage.core.mapper.BaseGeneratedMapper;
import com.backstage.core.result.ServiceResult;
import com.backstage.core.service.AbstractBaseAOService;
import com.backstage.system.dao.gen.RolePrivilegeGeneratedMapper;
import com.backstage.system.entity.gen.RolePrivilegeCriteria;
import com.backstage.system.entity.customized.RolePrivilegeAO;
import com.backstage.system.service.IRolePrivilegeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class RolePrivilegeService extends AbstractBaseAOService<RolePrivilegeAO, RolePrivilegeCriteria> implements IRolePrivilegeService {

    @Resource
    private RolePrivilegeGeneratedMapper rolePrivilegeGeneratedMapper;

    @Override
    protected BaseGeneratedMapper<RolePrivilegeAO, RolePrivilegeCriteria> getGeneratedMapper() {
        return rolePrivilegeGeneratedMapper;
    }

    /**
     * 根据角色id删除权限
     * @param roleId
     * @return
     */
    public ServiceResult<Boolean> deleteByRoleId(String roleId){
        RolePrivilegeCriteria criteria=new RolePrivilegeCriteria();
        criteria.createCriteria().andRoleIdEqualTo(roleId);
        return deleteByCriteria(criteria);
    }

    /**
     * 获取角色拥有的权限
     * @param roleId 角色ID
     * @return
     */
    public ServiceResult<List<RolePrivilegeAO>> getRolePrivilege(String roleId){
        RolePrivilegeCriteria criteria=new RolePrivilegeCriteria();
        criteria.createCriteria().andRoleIdEqualTo(roleId);
        return  selectByCriteria(criteria);
    }
}
