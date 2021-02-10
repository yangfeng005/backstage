package com.backstage.system.service;

import com.backstage.core.result.ServiceResult;
import com.backstage.core.service.IBaseAOService;
import com.backstage.system.dto.request.SysLogRequest;
import com.backstage.system.entity.customized.SysLogAO;
import com.backstage.system.entity.gen.SysLogCriteria;

import java.util.List;

public interface ISysLogService extends IBaseAOService<SysLogAO, SysLogCriteria> {


    /**
     * 查询操作日志
     *
     * @return
     */
    ServiceResult<List<SysLogAO>> list(SysLogRequest request);


    /**
     * 删除历史数据，只保留最近一年
     *
     * @return
     */
    ServiceResult<Boolean> deleteHistory();
}
