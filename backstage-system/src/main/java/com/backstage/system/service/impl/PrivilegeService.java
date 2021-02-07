package com.backstage.system.service.impl;

import com.backstage.core.mapper.BaseGeneratedMapper;
import com.backstage.core.service.AbstractBaseAOService;
import com.backstage.system.dao.gen.PrivilegeGeneratedMapper;
import com.backstage.system.entity.customized.PrivilegeAO;
import com.backstage.system.entity.gen.PrivilegeCriteria;
import com.backstage.system.service.IPrivilegeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class PrivilegeService extends AbstractBaseAOService<PrivilegeAO, PrivilegeCriteria> implements IPrivilegeService {

    @Resource
    private PrivilegeGeneratedMapper privilegeGeneratedMapper;

    @Override
    protected BaseGeneratedMapper<PrivilegeAO, PrivilegeCriteria> getGeneratedMapper() {
        return privilegeGeneratedMapper;
    }

}
