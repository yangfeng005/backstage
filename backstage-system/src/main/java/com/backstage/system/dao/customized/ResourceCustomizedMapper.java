package com.backstage.system.dao.customized;

import com.backstage.core.mapper.BaseCustomizedMapper;
import com.backstage.system.entity.customized.ResourceAO;
import com.backstage.system.entity.gen.ResourceCriteria;

import java.util.List;

/**
 * Resource 数据存取接口.
 *
 * @author yangfeng
 * @version 1.0.0, Jun 21, 2018
 */
public interface ResourceCustomizedMapper extends BaseCustomizedMapper<ResourceAO, ResourceCriteria> {

    List<ResourceAO> getParentResourceByLeaf();

    /**
     * 权限和资源表整合成总的资源
     *
     * @return
     */
    List<ResourceAO> getResourceWithPrivilege();


    /**
     * 根据用户名称获取与权限相关的资源
     *
     * @return
     */
    List<ResourceAO> getResourceByUserName(String userName);
}
