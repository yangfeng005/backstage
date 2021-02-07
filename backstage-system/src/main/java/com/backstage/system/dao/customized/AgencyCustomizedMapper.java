package com.backstage.system.dao.customized;

import com.backstage.system.dto.request.AgencyRequest;
import com.backstage.system.entity.customized.AgencyAO;

import java.util.List;

/**
 * Agency 数据存取接口.
 *
 * @author yangfeng
 * @version 1.0.0, Jul 17, 2019
 */
public interface AgencyCustomizedMapper {

    List<AgencyAO> list(AgencyRequest request);


    /**
     * 编辑时选择上级机构要排除自身
     *
     * @param id
     * @return
     */
    List<AgencyAO> listOthers(String id);


    /**
     * 批量删除
     *
     * @param agencyAOList
     * @return
     */
    int deleteBatch(List<AgencyAO> agencyAOList);
}
