package com.backstage.system.service;

import com.backstage.core.result.ServiceResult;
import com.backstage.core.service.IBaseAOService;
import com.backstage.system.dto.request.AgencyRequest;
import com.backstage.system.entity.customized.AgencyAO;
import com.backstage.system.entity.gen.AgencyCriteria;

import java.util.List;

public interface IAgencyService extends IBaseAOService<AgencyAO, AgencyCriteria> {


    /**
     * 查询机构
     *
     * @return
     */
    ServiceResult<List<AgencyAO>> list(AgencyRequest request);


    /**
     * 保存
     *
     * @param user
     * @return
     */
    ServiceResult<Boolean> save(AgencyAO user);

    /**
     * 编辑时选择上级机构要排除自身
     *
     * @param id
     * @return
     */
    ServiceResult<List<AgencyAO>> listOthers(String id);


    /**
     * 删除机构及所有子机构
     *
     * @param agencyId
     * @return
     */
    ServiceResult<Boolean> delete(String agencyId);


    /**
     * 根据编码获取机构
     *
     * @param code
     * @return
     */
    AgencyAO getByCode(String code);
}
