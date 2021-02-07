package com.backstage.system.service.impl;

import com.backstage.core.constant.Constant;
import com.backstage.common.utils.lang.DateTimeUtil;
import com.backstage.core.mapper.BaseGeneratedMapper;
import com.backstage.core.result.ServiceResult;
import com.backstage.core.result.ServiceResultHelper;
import com.backstage.core.service.AbstractBaseAOService;
import com.backstage.system.auth.AuthUtil;
import com.backstage.system.dao.customized.RoleCustomizedMapper;
import com.backstage.system.dao.gen.RoleGeneratedMapper;
import com.backstage.system.dto.request.RoleRequest;
import com.backstage.system.entity.customized.RoleAO;
import com.backstage.system.entity.gen.RoleCriteria;
import com.backstage.system.entity.customized.RolePrivilegeAO;
import com.backstage.system.service.IRolePrivilegeService;
import com.backstage.system.service.IRoleService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class RoleService extends AbstractBaseAOService<RoleAO, RoleCriteria> implements IRoleService {

    @Resource
    private RoleGeneratedMapper roleGeneratedMapper;

    @Resource
    private RoleCustomizedMapper roleCustomizedMapper;

    @Resource
    private IRolePrivilegeService rolePrivilegeService;

    @Override
    protected BaseGeneratedMapper<RoleAO, RoleCriteria> getGeneratedMapper() {
        return roleGeneratedMapper;
    }

    /**
     * 根据用户id查询角色
     *
     * @param userId
     * @return
     */
    @Override
    public ServiceResult<List<RoleAO>> getRolesByUserId(String userId) {
        return ServiceResultHelper.genResultWithSuccess(roleCustomizedMapper.getRolesByUserId(userId));
    }

    /**
     * 根据用户名查询角色
     *
     * @param userName
     * @return
     */
    @Override
    public ServiceResult<List<RoleAO>> getRolesByUserName(String userName) {
        return ServiceResultHelper.genResultWithSuccess(roleCustomizedMapper.getRolesByUserName(userName));
    }

    /**
     * 查询所有角色
     *
     * @return
     */
    @Override
    public ServiceResult<List<RoleAO>> listRoles(RoleRequest request) {
        request.setAgencyCode(AuthUtil.getCurrentUser().getAgencyCode());
        return ServiceResultHelper.genResultWithSuccess(roleCustomizedMapper.listByCondition(request));
    }

    /**
     * 保存角色
     *
     * @param role
     * @return
     */
    @Override
    @Transactional
    public ServiceResult<Boolean> save(RoleAO role) {
        if (!checkBeforeSave(role)) {
            return ServiceResultHelper.genResultWithFaild("角色编码已存在", -1);
        }
        if (StringUtils.isEmpty(role.getId())) { //新增
            //检查角色是否已经存在
            role.setCreater(AuthUtil.getCurrentUser().getId());
            role.setCreateTime(DateTimeUtil.format(new Date(), DateTimeUtil.YEAR_MONTH_DAY_HOUR_MINUTE_SECOND));
            role.setStatus(Constant.VALID_FLG);//默认启用
        } else {
            role.setUpdater(AuthUtil.getCurrentUser().getId());
            role.setUpdateTime(DateTimeUtil.format(new Date(), DateTimeUtil.YEAR_MONTH_DAY_HOUR_MINUTE_SECOND));
        }
        //保存角色
        return saveOrUpdate(role);
    }


    /**
     * 保存前进行检查
     *
     * @return
     */
    public boolean checkBeforeSave(RoleAO role) {
        RoleCriteria criteria = new RoleCriteria();
        if (StringUtils.isEmpty(role.getId())) {
            criteria.createCriteria().andCodeEqualTo(role.getCode())
                    .andAgencyCodeEqualTo(role.getAgencyCode()).andStatusNotEqualTo(Constant.INVALID_FLG);
        } else {
            criteria.createCriteria().andCodeEqualTo(role.getCode()).andAgencyCodeEqualTo(role.getAgencyCode())
                    .andIdNotEqualTo(role.getId()).andStatusNotEqualTo(Constant.INVALID_FLG);
        }
        ServiceResult<List<RoleAO>> result = selectByCriteria(criteria);
        if (result != null && result.isSucceed() && !CollectionUtils.isEmpty(result.getData())) {
            return false;
        }
        return true;
    }

    /**
     * 保存角色权限
     *
     * @param roleId
     * @param privilegeIds
     * @return
     */
    @Override
    public ServiceResult<Boolean> savePrivilege(String roleId, String[] privilegeIds) {
        //删除原来的关联关系
        rolePrivilegeService.deleteByRoleId(roleId);
        //角色权限关联
        if (privilegeIds != null && privilegeIds.length > 0) {
            for (String privilegeId : privilegeIds) {
                RolePrivilegeAO rolePrivilege = new RolePrivilegeAO();
                rolePrivilege.setPrivilegeId(privilegeId);
                rolePrivilege.setRoleId(roleId);
                rolePrivilegeService.insert(rolePrivilege);
            }
        }
        return ServiceResultHelper.genResultWithSuccess();
    }

    /**
     * 删除角色
     *
     * @param roleId
     * @return
     */
    @Override
    public ServiceResult<Boolean> deleteRole(String roleId) {
        return deleteById(roleId);
    }
}
