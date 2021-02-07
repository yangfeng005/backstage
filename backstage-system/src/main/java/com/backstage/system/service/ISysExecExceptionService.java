package com.backstage.system.service;

import com.backstage.core.result.ServiceResult;
import com.backstage.core.service.IBaseAOService;
import com.backstage.system.entity.customized.SysExecExceptionAO;
import com.backstage.system.entity.gen.SysExecExceptionCriteria;

public interface ISysExecExceptionService extends IBaseAOService<SysExecExceptionAO, SysExecExceptionCriteria> {


    /**
     * 保存
     *
     * @param sysExecExceptionAO
     * @return
     */
    ServiceResult<Boolean> save(SysExecExceptionAO sysExecExceptionAO);


}
