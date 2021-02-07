package com.backstage.system.service.impl;


import com.backstage.core.mapper.BaseGeneratedMapper;
import com.backstage.core.service.AbstractBaseAOService;
import com.backstage.system.dao.gen.TimeCostGeneratedMapper;
import com.backstage.system.entity.customized.TimeCostAO;
import com.backstage.system.entity.gen.TimeCostCriteria;
import com.backstage.system.service.ITimeCostService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class TimeCostService extends AbstractBaseAOService<TimeCostAO, TimeCostCriteria> implements ITimeCostService {

    @Resource
    private TimeCostGeneratedMapper timeCostGeneratedMapper;


    @Override
    protected BaseGeneratedMapper<TimeCostAO, TimeCostCriteria> getGeneratedMapper() {
        return timeCostGeneratedMapper;
    }


}
