package com.backstage.system.service;

import com.backstage.core.result.ServiceResult;
import com.backstage.core.service.IBaseAOService;
import com.backstage.system.entity.gen.SysLogCriteria;
import com.backstage.system.entity.customized.SysLogAO;

public interface ISysLogService extends IBaseAOService<SysLogAO, SysLogCriteria> {


    /**
     * 删除历史数据，只保留最近一年
     *
     * @return
     */
    ServiceResult<Boolean> deleteHistory();
}
