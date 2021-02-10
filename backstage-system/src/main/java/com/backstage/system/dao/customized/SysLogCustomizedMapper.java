package com.backstage.system.dao.customized;


import com.backstage.system.dto.request.SysLogRequest;
import com.backstage.system.entity.customized.SysLogAO;

import java.util.List;

/**
 * 数据存取接口.
 *
 * @author yangfeng
 * @version 1.0.0, Jul 17, 2019
 */
public interface SysLogCustomizedMapper {

    List<SysLogAO> listByCondition(SysLogRequest request);

}
