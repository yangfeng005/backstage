package com.backstage.system.service.impl;


import com.backstage.core.mapper.BaseGeneratedMapper;
import com.backstage.core.result.ServiceResult;
import com.backstage.core.service.AbstractBaseAOService;
import com.backstage.system.dao.gen.VUserPrivilegeGeneratedMapper;
import com.backstage.system.entity.customized.VUserPrivilegeAO;
import com.backstage.system.entity.gen.VUserPrivilegeCriteria;
import com.backstage.system.service.IVUserPrivilegeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class VUserPrivilegeService extends AbstractBaseAOService<VUserPrivilegeAO, VUserPrivilegeCriteria> implements IVUserPrivilegeService {
    @Resource
    private VUserPrivilegeGeneratedMapper vUserPrivilegeGeneratedMapper;

    @Override
    protected BaseGeneratedMapper<VUserPrivilegeAO, VUserPrivilegeCriteria> getGeneratedMapper() {
        return vUserPrivilegeGeneratedMapper;
    }

    /**
     * 根据用户名获取用户权限
     *
     * @param userName
     * @return
     */
    @Override
    public List<VUserPrivilegeAO> queryPrivilegeByUserName(String userName) {
        VUserPrivilegeCriteria criteria = new VUserPrivilegeCriteria();
        criteria.createCriteria().andUserNameEqualTo(userName).andPrivilegeCodeIsNotNull();
        ServiceResult<List<VUserPrivilegeAO>> result = selectByCriteria(criteria);
        if (null != result && result.isSucceed() && result.getData() != null) {
            return result.getData();
        }
        return null;
    }
}
