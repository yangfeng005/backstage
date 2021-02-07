package com.backstage.system.service.impl;


import com.backstage.core.mapper.BaseGeneratedMapper;
import com.backstage.core.result.ServiceResult;
import com.backstage.core.service.AbstractBaseAOService;
import com.backstage.system.dao.gen.UserRoleGeneratedMapper;
import com.backstage.system.entity.gen.UserRoleCriteria;
import com.backstage.system.entity.customized.UserRoleAO;
import com.backstage.system.service.IUserRoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


@Service
public class UserRoleService extends AbstractBaseAOService<UserRoleAO, UserRoleCriteria> implements IUserRoleService {


    @Resource
    private UserRoleGeneratedMapper userRoleGeneratedMapper;

    @Override
    protected BaseGeneratedMapper<UserRoleAO, UserRoleCriteria> getGeneratedMapper() {
        return userRoleGeneratedMapper;
    }

    /**
     * 根据用户id获取用户角色
     *
     * @param userId
     * @return
     */
    @Override
    public List<UserRoleAO> getUserRoles(String userId) {
        UserRoleCriteria criteria = new UserRoleCriteria();
        criteria.createCriteria().andUserIdEqualTo(userId);
        ServiceResult<List<UserRoleAO>> result = selectByCriteria(criteria);
        if (null != result && result.isSucceed() && result.getData() != null) {
            return result.getData();
        }
        return null;
    }

    /**
     * 删除用户关联的角色
     *
     * @param userId
     * @return
     */
    public ServiceResult<Boolean> deleteByUserId(String userId) {
        UserRoleCriteria criteria = new UserRoleCriteria();
        criteria.createCriteria().andUserIdEqualTo(userId);
        return deleteByCriteria(criteria);
    }

}
