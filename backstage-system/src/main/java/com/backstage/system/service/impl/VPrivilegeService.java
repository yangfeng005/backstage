package com.backstage.system.service.impl;


import com.backstage.core.mapper.BaseGeneratedMapper;
import com.backstage.core.service.AbstractBaseAOService;
import com.backstage.system.dao.gen.VPrivilegeGeneratedMapper;
import com.backstage.system.entity.customized.VPrivilegeAO;
import com.backstage.system.entity.gen.VPrivilegeCriteria;
import com.backstage.system.service.IVPrivilegeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class VPrivilegeService extends AbstractBaseAOService<VPrivilegeAO, VPrivilegeCriteria> implements IVPrivilegeService {

    @Resource
    private VPrivilegeGeneratedMapper vPrivilegeGeneratedMapper;

    @Override
    protected BaseGeneratedMapper<VPrivilegeAO, VPrivilegeCriteria> getGeneratedMapper() {
        return vPrivilegeGeneratedMapper;
    }

    /**
     * 根据资源id获取权限
     * @param resourceId
     * @return
     */
    public List<VPrivilegeAO> getPrivilegesByResourceId(String resourceId){
        VPrivilegeCriteria criteria =new VPrivilegeCriteria();
        criteria.createCriteria().andResourceIdEqualTo(resourceId);
        return vPrivilegeGeneratedMapper.selectByCriteria(criteria);
    }
}
