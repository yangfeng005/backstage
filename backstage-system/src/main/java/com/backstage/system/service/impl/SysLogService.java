package com.backstage.system.service.impl;


import com.backstage.common.utils.lang.DateTimeUtil;
import com.backstage.core.mapper.BaseGeneratedMapper;
import com.backstage.core.result.ServiceResult;
import com.backstage.core.service.AbstractBaseAOService;
import com.backstage.system.dao.gen.SysLogGeneratedMapper;
import com.backstage.system.entity.gen.SysLogCriteria;
import com.backstage.system.entity.customized.SysLogAO;
import com.backstage.system.service.ISysLogService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class SysLogService extends AbstractBaseAOService<SysLogAO, SysLogCriteria> implements ISysLogService {

    @Resource
    private SysLogGeneratedMapper sysLogGeneratedMapper;


    @Override
    protected BaseGeneratedMapper<SysLogAO, SysLogCriteria> getGeneratedMapper() {
        return sysLogGeneratedMapper;
    }


    /**
     * 删除历史数据，只保留最近一年
     *
     * @return
     */
    @Override
    public ServiceResult<Boolean> deleteHistory() {
        String pastDate = DateTimeUtil.getPastOrAfterDate(-365);
        SysLogCriteria criteria = new SysLogCriteria();
        criteria.createCriteria().andOperateTimeLessThanOrEqualTo(pastDate);
        return deleteByCriteria(criteria);
    }
}
