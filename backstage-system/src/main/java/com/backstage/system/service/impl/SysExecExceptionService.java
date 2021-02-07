package com.backstage.system.service.impl;

import com.backstage.core.mapper.BaseGeneratedMapper;
import com.backstage.core.result.ServiceResult;
import com.backstage.core.service.AbstractBaseAOService;
import com.backstage.system.dao.gen.SysExecExceptionGeneratedMapper;
import com.backstage.system.entity.customized.SysExecExceptionAO;
import com.backstage.system.entity.gen.SysExecExceptionCriteria;
import com.backstage.system.service.ISysExecExceptionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 系统执行异常服务
 *
 * @author yangfeng
 * @date 2019-12-27
 */
@Service
public class SysExecExceptionService extends AbstractBaseAOService<SysExecExceptionAO, SysExecExceptionCriteria> implements ISysExecExceptionService {

    @Resource
    private SysExecExceptionGeneratedMapper sysExecExceptionGeneratedMapper;


    @Override
    protected BaseGeneratedMapper<SysExecExceptionAO, SysExecExceptionCriteria> getGeneratedMapper() {
        return sysExecExceptionGeneratedMapper;
    }


    /**
     * 保存
     *
     * @param SysExecException
     * @return
     */
    @Override
    public ServiceResult<Boolean> save(SysExecExceptionAO SysExecException) {
        return insert(SysExecException);
    }

}
