package com.backstage.system.service.impl;


import com.backstage.common.page.Page;
import com.backstage.common.utils.lang.DateTimeUtil;
import com.backstage.core.mapper.BaseGeneratedMapper;
import com.backstage.core.result.ServiceResult;
import com.backstage.core.service.AbstractBaseAOService;
import com.backstage.system.dao.customized.SysLogCustomizedMapper;
import com.backstage.system.dao.gen.SysLogGeneratedMapper;
import com.backstage.system.dto.request.SysLogRequest;
import com.backstage.system.entity.customized.SysLogAO;
import com.backstage.system.entity.gen.SysLogCriteria;
import com.backstage.system.service.ISysLogService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SysLogService extends AbstractBaseAOService<SysLogAO, SysLogCriteria> implements ISysLogService {

    @Resource
    private SysLogGeneratedMapper sysLogGeneratedMapper;


    @Resource
    private SysLogCustomizedMapper sysLogCustomizedMapper;


    @Override
    protected BaseGeneratedMapper<SysLogAO, SysLogCriteria> getGeneratedMapper() {
        return sysLogGeneratedMapper;
    }


    /**
     * 查询操作日志
     *
     * @return
     */
    @Override
    public ServiceResult<List<SysLogAO>> list(SysLogRequest request) {
        ServiceResult<List<SysLogAO>> ret = new ServiceResult<>();
        PageHelper.startPage(request.getPageNo(), request.getPageSize());
        List<SysLogAO> logAOList = sysLogCustomizedMapper.listByCondition(request);
        ret.setData(logAOList);
        ret.setSucceed(true);
        ret.setAdditionalProperties("page", Page.obtainPage(new PageInfo<>(logAOList)));
        return ret;
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
